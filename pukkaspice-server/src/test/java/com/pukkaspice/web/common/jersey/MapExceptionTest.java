package com.pukkaspice.web.common.jersey;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

public class MapExceptionTest {
    
    private ExceptionMapperImpl exceptionMapperImpl = new ExceptionMapperImpl();

    
    @Test
    public void should_map_unauthorised_app_exceptions_to_401_response() {
        Response response = exceptionMapperImpl.toResponse(new UnauthorisedException("expected exception"));
        assertEquals("Expected response to be an 401 but it wasn't.", response.getStatusInfo(), Status.UNAUTHORIZED);
        
        Response responseNoMessage = exceptionMapperImpl.toResponse(new UnauthorisedException());
        assertEquals("Expected response to be an 401 but it wasn't.", responseNoMessage.getStatusInfo(), Status.UNAUTHORIZED);
    }
    
    @Test
    public void should_map_InsuficientPermissionsException_exceptions_to_403_response() throws Exception {
        Response response = exceptionMapperImpl.toResponse(new InsuficientPermissionsException());
        assertEquals("Expected response to 403 but it wasn't.", response.getStatusInfo(), Status.FORBIDDEN);
    }
    
    @Test
    public void should_map_unexpected_exceptions_to_500_response() throws Exception {
        Response response = exceptionMapperImpl.toResponse(new RuntimeException("unexpected exception"));
        assertEquals("Expected response to 500 but it wasn't.", response.getStatusInfo(), Status.INTERNAL_SERVER_ERROR);
    }

}
