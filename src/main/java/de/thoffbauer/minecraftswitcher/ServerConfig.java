package de.thoffbauer.minecraftswitcher;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class ServerConfig extends Configuration {

    @NotNull
    private String serverDir;

    @JsonProperty
    public String getServerDir() {
        return serverDir;
    }

    @JsonProperty
    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }
}
