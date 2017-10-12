package de.thoffbauer.minecraftswitcher.pages.users;

import de.thoffbauer.minecraftswitcher.auth.Authenticator;
import de.thoffbauer.minecraftswitcher.auth.Authorizer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.TEXT_HTML)
public class UsersPage {

    private Authenticator authenticator;

    public UsersPage(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @RolesAllowed(Authorizer.ROLE_ADMIN)
    @GET
    public Response get() {
        return Response.ok(new UsersView(authenticator.getUsers())).build();
    }

}
