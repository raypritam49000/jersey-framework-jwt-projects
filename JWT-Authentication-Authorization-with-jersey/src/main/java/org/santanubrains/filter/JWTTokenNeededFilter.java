package org.santanubrains.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.santanubrains.authentication.JWTTokenNeeded;
import org.santanubrains.dataAdapter.CredentialDataAdapter;
import org.santanubrains.log4j.Log4jUtil;
import org.santanubrains.response.AuthorizationMessage;
import org.santanubrains.utiliy.AuthenticationConfig;
import org.santanubrains.utiliy.KeyGenerator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

	private static final Logger logger = Log4jUtil.getLogger(JWTTokenNeededFilter.class);
	private static String subject = null;
	@Context
	private ResourceInfo resourceInfo;

	private final KeyGenerator keyGenerator;

	private final CredentialDataAdapter credentialDataAdapter;

	@Inject
	public JWTTokenNeededFilter(KeyGenerator keyGenerator,  CredentialDataAdapter credentialDataAdapter) {
		super();
		this.keyGenerator = keyGenerator;
		this.credentialDataAdapter = credentialDataAdapter;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		Method method = resourceInfo.getResourceMethod();

		// Access allowed for all
		if (!method.isAnnotationPresent(PermitAll.class)) {

			if (method.isAnnotationPresent(DenyAll.class)) {
				Response access_denied = Response.status(Response.Status.FORBIDDEN)
						.entity(new AuthorizationMessage(AuthenticationConfig.FORBIDDEN_STATUS_CODE,
								AuthenticationConfig.FORBIDDEN_STATUS_MESSAGE,
								AuthenticationConfig.FORBIDDEN_STATUS_DETAIL))
						.build();
				requestContext.abortWith(access_denied);
				return;
			}

			// Get the HTTP Authorization header from the request
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
			logger.info("#### authorizationHeader : " + authorizationHeader);

			// Check if the HTTP Authorization header is present and formatted correctly
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				logger.error("#### invalid authorizationHeader : " + authorizationHeader);
				throw new NotAuthorizedException("Authorization header must be provided");
			}

			// Extract the token from the HTTP Authorization header
			String token = authorizationHeader.substring("Bearer".length()).trim();

			try {

				// Validate the token
				Key key = keyGenerator.generateKey();
				Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
				subject = jws.getBody().getSubject();
				logger.info("#### valid token : " + token + " " + subject);

			} catch (Exception e) {
				logger.error("#### invalid token : " + token);
				Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
						.entity(new AuthorizationMessage(AuthenticationConfig.DENIED_STATUS_CODE,
								AuthenticationConfig.DENIED_STATUS_MESSAGE, AuthenticationConfig.DENIED_STATUS_DETAIL))
						.build();
				requestContext.abortWith(unauthorizedStatus);
				return;
			}

			// Verify user access
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				// Is user valid?
				if (!isUserAllowed(rolesSet, subject)) {

					Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
							.entity(new AuthorizationMessage(AuthenticationConfig.DENIED_STATUS_CODE,
									AuthenticationConfig.DENIED_STATUS_MESSAGE,
									AuthenticationConfig.DENIED_STATUS_DETAIL))
							.build();
					requestContext.abortWith(unauthorizedStatus);
					return;
				}
			}

		}

	}

	private boolean isUserAllowed(final Set<String> rolesSet, String subject) {
		boolean isAllowed = false;

		if (!subject.equals(null)) {
			List<String> userRole = credentialDataAdapter.getUserRoles(subject);
			for (String role : userRole) {
				if (rolesSet.contains(role.toUpperCase())) {
					isAllowed = true;
				}
			}
		}
		return isAllowed;
	}
}
