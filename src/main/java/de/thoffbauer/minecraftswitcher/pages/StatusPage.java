package de.thoffbauer.minecraftswitcher.pages;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.TEXT_HTML)
@Path("/")
public class StatusPage {

    @GET
    public Response get() {
        return Response.ok(new ControlView(null, null)).build();
    }

}
