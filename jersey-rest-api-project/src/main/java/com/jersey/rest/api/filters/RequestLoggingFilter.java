package com.jersey.rest.api.filters;

import org.apache.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RequestLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private long startTime;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        startTime = System.currentTimeMillis();
        LOGGER.info("Request Start Time : "+ startTime);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        long endTime = System.currentTimeMillis();
        LOGGER.info("Request End Time : "+ endTime);
        long duration = endTime - startTime;
        LOGGER.info("----------- Request Duration: -------------" + duration + " ms");
    }
}
