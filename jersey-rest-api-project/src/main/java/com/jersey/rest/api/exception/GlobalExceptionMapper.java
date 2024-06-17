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
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Exception ex) {

        LOGGER.error("---------------- Handle Global Exception ----------------");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .entity(Map.<String, Object>of("status", Response.Status.INTERNAL_SERVER_ERROR, "statusCode", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "message", ex.getMessage(),"path", uriInfo.getRequestUri().toString(), "date", new Date().toString()))
                    .build();
    }
}
