package com.jersey.rest.api.filters;

import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.security.TokenBasedSecurityContext;
import com.jersey.rest.api.service.UserService;
import com.jersey.rest.api.service.impl.UserServiceImpl;
import com.jersey.rest.api.utils.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

@Provider
@Priority(Priorities.AUTHENTICATION)
@JWTTokenNeeded
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECURITY_CONTEXT_PROPERTY = "securityContextProperty";
    private final UserService userService = new UserServiceImpl();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        try {
            UserDTO userDto = JwtUtility.getUserDtoFromToken(token);
            UserDTO userByUsername = userService.findByUsername(userDto.getUsername());
            TokenBasedSecurityContext tokenBasedSecurityContext = new TokenBasedSecurityContext(userByUsername, requestContext.getSecurityContext().isSecure());
            requestContext.setProperty(SECURITY_CONTEXT_PROPERTY, tokenBasedSecurityContext);
            requestContext.setSecurityContext(tokenBasedSecurityContext);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                .entity(Map.of("status", Response.Status.FORBIDDEN, "statusCode", Response.Status.FORBIDDEN.getStatusCode(), "message", "Forbidden User"))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}