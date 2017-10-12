package de.thoffbauer.minecraftswitcher.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Authenticator implements io.dropwizard.auth.Authenticator<BasicCredentials, User> {

    private static final Logger logger = LoggerFactory.getLogger(Authenticator.class);

    private static final File USER_STORE = new File("users.store");

    private final HashMap<String, String> tokens = new HashMap<>();
    private final HashMap<String, User> users = new HashMap<>();
    private final PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    public void load() {
        synchronized(users) {
            try {
                USER_STORE.createNewFile();
                tokens.clear();
                BufferedReader reader = new BufferedReader(new FileReader(USER_STORE));
                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.startsWith("#")) {
                        continue;
                    }
                    String[] split = line.split(" *; *");
                    if(split.length != 4) {
                        throw new IOException("Bad format in user store!");
                    }
                    Set<String> servers = Arrays.stream(split[2].split(" *, *")).collect(Collectors.toSet());
                    users.put(split[0], new User(split[0], Boolean.valueOf(split[1]), servers));
                    tokens.put(split[0], split[3]);
                }
                reader.close();
            } catch (IOException e) {
                logger.error("Could not load users!", e);
            }
        }
    }

    public boolean addUser(User user, String password) {
        synchronized (users) {
            if(tokens.containsKey(user.getName())) {
                return false;
            }
            String token = passwordAuthentication.hash(password.toCharArray());
            users.put(user.getName(), user);
            tokens.put(user.getName(), token);
            save();
            logger.info("User {} added.", user.getName());
            return true;
        }
    }

    public void updatePassword(String username, String password) {
        synchronized (users) {
            if(!tokens.containsKey(username)) {
                throw new IllegalArgumentException("Uknown user!");
            }
            tokens.put(username, passwordAuthentication.hash(password.toCharArray()));
            logger.info("User {} updated password.", username);
            save();
        }
    }

    public void onUserUpdated() {
        synchronized (users) {
            save();
        }
    }

    private void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_STORE));
            for (Map.Entry<String, User> entry : users.entrySet()) {
                writer.write(String.join("; ",
                        entry.getKey(),
                        String.valueOf(entry.getValue().isAdmin()),
                        String.join(", ", entry.getValue().getServers()),
                        tokens.get(entry.getKey())));
                writer.write("\n");
            }
            writer.close();
            logger.debug("Users saved.");
        } catch (IOException e) {
            logger.error("Could not save users. Changes will be lost on next restart!", e);
        }
    }

    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if(tokens.isEmpty()) {
            User user = new User(credentials.getUsername(), true, new HashSet<>());
            users.put(user.getName(), user);
            tokens.put(user.getName(), passwordAuthentication.hash(credentials.getPassword().toCharArray()));
            save();
            return Optional.of(user);
        }
        String token = tokens.get(credentials.getUsername());
        if(token != null && passwordAuthentication.authenticate(credentials.getPassword().toCharArray(), token)) {
            return Optional.of(users.get(credentials.getUsername()));
        } else {
            return Optional.empty();
        }
    }
}
