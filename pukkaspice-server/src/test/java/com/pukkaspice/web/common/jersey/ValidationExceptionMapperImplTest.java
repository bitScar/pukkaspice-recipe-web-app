package com.pukkaspice.web.common.jersey;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;


public class ValidationExceptionMapperImplTest {
    
    private ValidationExceptionMapperImpl validationExceptionMapperImpl;
    
    @Before
    public void setUp() {
        validationExceptionMapperImpl = new ValidationExceptionMapperImpl();
    }
    
    @Test
    public void should_respond_with_400_bad_request_if_passed_there_is_constraint_validation_exception() {
        ValidationException validationException = new ConstraintViolationException("Test validation exception", new HashSet<ConstraintViolation<?>>());
        
        Response response = validationExceptionMapperImpl.toResponse(validationException);
        
        int statusCode = response.getStatus();
        
        assertEquals(Status.BAD_REQUEST.getStatusCode(), statusCode);
        assertEquals(400, statusCode);
        
        ExceptionReponse exceptionReponse = (ExceptionReponse) response.getEntity();
        assertEquals(0, exceptionReponse.getValidationErrors().size());
    }
    
    @Test
    public void should_respond_with_500_internal_server_error_if_passed_anything_else_but_a_constraint_validation_exception() {
        ValidationException validationException = new ConstraintDefinitionException();
        
        Response response = validationExceptionMapperImpl.toResponse(validationException);
        
        int statusCode = response.getStatus();
        
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), statusCode);
        assertEquals(500, statusCode);
    }
    
}
