package de.thoffbauer.minecraftswitcher.pages;

import de.thoffbauer.minecraftswitcher.MinecraftController;
import de.thoffbauer.minecraftswitcher.auth.User;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class ControlPage {

    private static final Logger logger = LoggerFactory.getLogger(ControlPage.class);

    @GET
    public Response get(@Auth Optional<User> user) {
        return Response.ok(new ControlView(user.orElse(null), null)).build();
    }

    @POST
    public Response post(@Auth User user, @FormParam("button") String button) {
        MinecraftController minecraftController = MinecraftController.getInstance();
        Set<String> runningServers = minecraftController.getRunningServers();
        String[] parts = button.split("\\.");
        String server = parts[0];
        if(!user.isAdmin() && !user.getServers().contains(server)) {
            return Response.status(403).build();
        }
        if(!Arrays.asList(minecraftController.getServerNames()).contains(server)) {
            return Response.status(404).build();
        }
        try {
            if(parts[1].equals("start")) {
                if(runningServers.contains(server)) {
                    return Response.ok(new ControlView(user, "Server already running!")).build();
                } else {
                    minecraftController.start(server);
                }
            } else if(parts[1].equals("stop")) {
                if(runningServers.contains(server)) {
                    minecraftController.stop(server);
                } else {
                    return Response.ok(new ControlView(user, "Server already stopped!")).build();
                }
            }
        } catch (InterruptedException e) {
            logger.error("Could not start or stop server, because the script is not responding!", e);
            return Response.status(503).build();
        }
        return Response.ok(new ControlView(user, null)).build();
    }

}
