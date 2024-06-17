package com.jersey.rest.api.exception;

import org.apache.log4j.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;
import java.util.Map;

@Provider
public class ResourceConflictExceptionMapper implements ExceptionMapper<ResourceConflictException> {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ResourceConflictException exception) {
        LOGGER.error("------------------ Handle ResourceConflictExceptionMapper -------------------");

        return Response.status(Response.Status.CONFLICT)
                .status(Response.Status.CONFLICT.getStatusCode())
                .entity(Map.<String, Object>of("status", Response.Status.CONFLICT, "statusCode", Response.Status.CONFLICT.getStatusCode(), "message", exception.getMessage(), "path", uriInfo.getRequestUri().toString(), "date", new Date().toString()))
                .build();
    }
}
