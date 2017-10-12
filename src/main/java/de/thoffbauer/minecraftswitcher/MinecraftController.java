package de.thoffbauer.minecraftswitcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MinecraftController {

    private static final Logger logger = LoggerFactory.getLogger(MinecraftController.class);

    private static final int TIMEOUT = 10000;

    private static MinecraftController INSTANCE;

    public static void setInstance(MinecraftController instance) {
        MinecraftController.INSTANCE = instance;
    }

    public static MinecraftController getInstance() {
        return INSTANCE;
    }

    private File serverDir;

    public MinecraftController(File serverDir) throws FileNotFoundException {
        this.serverDir = serverDir;
        logger.info("Using server directory \"{}\"", serverDir.getPath());
        if(!serverDir.isDirectory()) {
            throw new FileNotFoundException("Server directory not found!");
        }
    }

    public String[] getServerNames() {
        return serverDir.list();
    }

    public void start(String server) throws InterruptedException {
        logger.info("Starting {}", server);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(serverDir.getAbsolutePath() + "/" + server + "/start.sh");
            process.wait(TIMEOUT);
            if(process.isAlive()) {
                throw new RuntimeException("Start command timed out.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getRunningServers() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(serverDir.getAbsolutePath() + "/list.sh");
            Set<String> servers = new HashSet<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                servers.add(line);
            }
            return servers;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(String server) throws InterruptedException {
        logger.info("Stopping {}.", server);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[] {serverDir.getAbsolutePath() + "/stop.sh", server});
            process.wait(TIMEOUT);
            if(process.isAlive()) {
                throw new RuntimeException("Start command timed out.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
