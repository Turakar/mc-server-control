package de.thoffbauer.minecraftswitcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        return serverDir.list((dir, name) -> new File(dir.getPath() + "/" + name).isDirectory());
    }

    public void start(String server) throws InterruptedException {
        logger.info("Starting {}", server);
        try {
            Process process = exec(serverDir.getAbsolutePath() + "/" + server, "start.sh");
            process.waitFor(TIMEOUT, TimeUnit.MILLISECONDS);
            if(process.isAlive()) {
                throw new RuntimeException("Start command timed out.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getRunningServers() throws InterruptedException {
        try {
            Process process = exec(serverDir.getAbsolutePath(), "list.sh");
            process.waitFor(TIMEOUT, TimeUnit.MILLISECONDS);
            Set<String> servers = new HashSet<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String prefix = serverDir.getAbsolutePath() + "/";
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith(prefix)) {
                    int beginIndex = line.lastIndexOf('/') + 1;
                    servers.add(line.substring(beginIndex));
                }
            }
            return servers;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(String server) throws InterruptedException {
        logger.info("Stopping {}.", server);
        try {
            Process process = exec(serverDir.getAbsolutePath(), "stop.sh", server);
            process.waitFor(TIMEOUT, TimeUnit.MILLISECONDS);
            if(process.isAlive()) {
                throw new RuntimeException("Stop command timed out.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Process exec(String dir, String... command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        logger.debug("Running command: {} in {}", command, dir);
        ProcessBuilder processBuilder = new ProcessBuilder();
        command[0] = dir + "/" + command[0];
        processBuilder.command(command);
        processBuilder.directory(new File(dir));
        Process process = processBuilder.start();
        return process;
    }
}
