package com.rest.api.controller;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/howdy")
public class HowdyController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @GET
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        LOGGER.info("---------- Hello Howdy!!! --------");
        return "Hello Howdy!!";
    }

}
