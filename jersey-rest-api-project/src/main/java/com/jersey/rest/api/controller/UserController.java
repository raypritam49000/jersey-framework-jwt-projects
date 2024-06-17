package com.jersey.rest.api.controller;

import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.filters.JWTTokenNeeded;
import com.jersey.rest.api.security.TokenBasedSecurityContext;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/user")
@JWTTokenNeeded
public class UserController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private static final String SECURITY_CONTEXT_PROPERTY = "securityContextProperty";

    @GET
    @Path("/currentUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response currentUser(@Context ContainerRequestContext requestContext, @Context SecurityContext securityContext) {
        TokenBasedSecurityContext tokenBasedSecurityContext = (TokenBasedSecurityContext) requestContext.getProperty(SECURITY_CONTEXT_PROPERTY);
        LOGGER.info("------- Current User Request --------" + ((UserDTO) securityContext.getUserPrincipal()));
        return Response.status(Response.Status.OK.getStatusCode()).status(Response.Status.OK).entity(tokenBasedSecurityContext.getUserPrincipal()).build();
    }
}
