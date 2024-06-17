package com.jersey.rest.api.config;

import com.jersey.rest.api.controller.AuthenticationController;
import com.jersey.rest.api.controller.EmployeeController;
import com.jersey.rest.api.controller.HowdyController;
import com.jersey.rest.api.controller.UserController;
import com.jersey.rest.api.exception.*;
import com.jersey.rest.api.filters.ContextListener;
import com.jersey.rest.api.filters.JWTTokenNeededFilter;
import com.jersey.rest.api.filters.RequestLoggingFilter;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(HowdyController.class);
        register(AuthenticationController.class);
        register(UserController.class);
        register(EmployeeController.class);
        register(ContextListener.class);
        register(MultiPartFeature.class);
        register(JWTTokenNeededFilter.class);
        register(RequestLoggingFilter.class);
        register(RolesAllowedDynamicFeature.class);

        // Logger Handling
        register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));

        // Exception Handling
        register(ForbiddenExceptionMapper.class);
        register(ResourceNotFoundExceptionMapper.class);
        register(ResourceConflictExceptionMapper.class);
        register(BadCredentialsExceptionMapper.class);
        register(GlobalExceptionMapper.class);
    }

}

