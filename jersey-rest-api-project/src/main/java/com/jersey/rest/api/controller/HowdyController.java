package com.jersey.rest.api.controller;

import com.jersey.rest.api.filters.JWTTokenNeeded;
import org.apache.log4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/howdy")
@JWTTokenNeeded
public class HowdyController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response howdy() {
        LOGGER.info("--------------- Hello Howdy --------------------");
        return Response.status(Response.Status.OK).status(Response.Status.OK.getStatusCode()).entity(Map.of("message", "Hello Howdy", "status", Response.Status.OK, "statusCode", Response.Status.OK.getStatusCode())).build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAdminMessage() {
        return "This is an admin message";
    }

    @GET
    @Path("/user")
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserMessage() {
        return "This is a user message";
    }

    @GET
    @Path("/public")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPublicMessage() {
        return "This is a public message";
    }

    @GET
    @Path("/manager")
    @RolesAllowed("MANAGER")
    @Produces(MediaType.TEXT_PLAIN)
    public String getManagerMessage() {
        return "This is a manager message";
    }
}
