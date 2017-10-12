package de.thoffbauer.minecraftswitcher.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {

    private String name;
    private boolean admin;
    private Set<String> servers;

    public User(String name, boolean admin, Set<String> servers) {
        this.name = name;
        this.admin = admin;
        this.servers = servers;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<String> getServers() {
        return servers;
    }

    public void setServers(Set<String> servers) {
        this.servers = servers;
    }
}
