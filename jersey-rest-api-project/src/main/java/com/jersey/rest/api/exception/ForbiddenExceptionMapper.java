package com.jersey.rest.api.exception;

import org.apache.log4j.Logger;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;
import java.util.Map;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ForbiddenException ex) {
        LOGGER.error("------------- Handle ForbiddenException ------------------");
        return Response.status(Response.Status.FORBIDDEN)
                .entity(Map.<String, Object>of("status", Response.Status.FORBIDDEN, "statusCode", Response.Status.FORBIDDEN.getStatusCode(), "message", ex.getMessage(), "path", uriInfo.getRequestUri().toString(), "date", new Date().toString()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}