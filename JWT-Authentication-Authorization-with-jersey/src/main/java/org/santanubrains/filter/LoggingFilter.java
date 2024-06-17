package org.santanubrains.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.santanubrains.log4j.Log4jUtil;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final Logger logger = Log4jUtil.getLogger(LoggingFilter.class);
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.info("Request Filter");
		logger.info("Headers : " + requestContext.getHeaders());

	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		logger.info("Response Filter");
		logger.info("Headers: " + responseContext.getHeaders());

	}

}
