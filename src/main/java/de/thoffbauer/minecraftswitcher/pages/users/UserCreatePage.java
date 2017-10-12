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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/users/create")
@Produces(MediaType.TEXT_HTML)
public class UserCreatePage {

    private Authenticator authenticator;

    public UserCreatePage(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @RolesAllowed(Authorizer.ROLE_ADMIN)
    @GET
    public Response get() {
        User defaultUser = new User("", false, new HashSet<>());
        return Response.ok(new UserCreateView(defaultUser, null)).build();
    }

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed(Authorizer.ROLE_ADMIN)
    @POST
    public Response post(MultivaluedMap<String, String> params) {
        Set<String> servers = params.keySet().stream()
                .filter(k -> k.startsWith("access."))
                .map(k -> k.substring(7))
                .collect(Collectors.toSet());
        User user = new User(params.getFirst("name"), params.containsKey("admin"), servers);
        boolean ok = authenticator.addUser(user, params.getFirst("password"));
        if(ok) {
            return Response.seeOther(URI.create("/users")).build();
        } else {
            return Response.ok(new UserCreateView(user, "There is already an user with this name!")).build();
        }
    }

}
