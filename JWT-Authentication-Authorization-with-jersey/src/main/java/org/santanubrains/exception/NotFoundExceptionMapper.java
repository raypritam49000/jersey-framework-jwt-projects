package org.santanubrains.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.santanubrains.response.ErrorResponse;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(ExceptionConfig.NOT_FOUND, exception.getMessage(),
				ExceptionConfig.ERROR_URL);
		return Response.status(Status.NOT_FOUND).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();

	}

}
