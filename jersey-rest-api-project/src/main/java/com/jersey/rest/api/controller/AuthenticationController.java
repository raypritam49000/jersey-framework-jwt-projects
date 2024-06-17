package com.jersey.rest.api.controller;

import com.jersey.rest.api.dto.JwtRequestDTO;
import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.service.UserService;
import com.jersey.rest.api.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationController {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private final UserService userService = new UserServiceImpl();

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDTO userDTO) {
        LOGGER.info("RegisterUser Request : "+userDTO);
        return Response.status(Response.Status.CREATED)
                .status(Response.Status.CREATED.getStatusCode())
                .entity(userService.registerUser(userDTO))
                .build();
    }


    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(JwtRequestDTO jwtRequestDTO) {
        LOGGER.info("LoginUser Request : "+jwtRequestDTO);
        return Response.status(Response.Status.OK)
                .status(Response.Status.OK.getStatusCode())
                .entity(userService.loginUser(jwtRequestDTO.getUsername(), jwtRequestDTO.getPassword()))
                .build();
    }


}
