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
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ResourceNotFoundException ex) {
        LOGGER.error("----------- Handle ResourceNotFoundException ------------");

      return Response.status(Response.Status.NOT_FOUND)
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .entity(Map.<String, Object>of("status", Response.Status.NOT_FOUND, "statusCode", Response.Status.NOT_FOUND.getStatusCode(), "message", ex.getMessage(),"path", uriInfo.getRequestUri().toString(), "date", new Date().toString()))
                .build();
    }
}
