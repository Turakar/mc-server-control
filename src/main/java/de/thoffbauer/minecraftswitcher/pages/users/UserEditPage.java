package de.thoffbauer.minecraftswitcher.pages.users;

import de.thoffbauer.minecraftswitcher.auth.Authenticator;
import de.thoffbauer.minecraftswitcher.auth.Authorizer;
import de.thoffbauer.minecraftswitcher.auth.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/user/{name}")
@Produces(MediaType.TEXT_HTML)
public class UserEditPage {

    private Authenticator authenticator;

    public UserEditPage(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @RolesAllowed(Authorizer.ROLE_ADMIN)
    @GET
    public Response get(@PathParam("name") String name) {
        User user = authenticator.getUser(name);
        if(user == null) {
            return Response.status(404).build();
        }
        return Response.ok(new UserEditView(user)).build();
    }

    @RolesAllowed(Authorizer.ROLE_ADMIN)
    @POST
    public Response post(@PathParam("name") String name, MultivaluedMap<String, String> params) {
        User user = authenticator.getUser(name);
        if(user == null) {
            return Response.status(404).build();
        }
        if(params.containsKey("password") && !params.getFirst("password").isEmpty()) {
            authenticator.updatePassword(name, params.getFirst("password"));
        }
        user.setAdmin(params.containsKey("admin"));
        Set<String> servers = params.keySet().stream()
                .filter(k -> k.startsWith("access."))
                .map(k -> k.substring(7))
                .collect(Collectors.toSet());
        user.setServers(servers);
        authenticator.onUserUpdated();
        return Response.seeOther(URI.create("/users")).build();
    }

}
