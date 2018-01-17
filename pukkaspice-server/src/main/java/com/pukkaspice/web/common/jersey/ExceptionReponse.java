package com.pukkaspice.web.common.jersey;

import java.util.List;

import org.glassfish.jersey.server.validation.ValidationError;


public class ExceptionReponse {
	
	private String exceptionMessage;
	
	private List<ValidationError> validationErrors;
	
	public ExceptionReponse() {
		super();
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

}
