package com.pukkaspice.web.common.jersey;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

@SuppressWarnings("serial")
public class ExceptionMapperImpl extends Throwable implements ExceptionMapper<Exception> {

	public Response toResponse(Exception exception) {
		Response response = null;
		
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		
		if (exception instanceof UnauthorisedException) {
			exceptionReponse.setExceptionMessage(exception.getMessage());
			response = Response.status(Status.UNAUTHORIZED).entity(exceptionReponse).build();
		} else if (exception instanceof InsuficientPermissionsException) {
		    exceptionReponse.setExceptionMessage("Insufficient permissions to do this operation");
		    response = Response.status(Status.FORBIDDEN).entity(exceptionReponse).build();
		} else {
			exception.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}

}
