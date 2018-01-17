package com.pukkaspice.web.common.jersey;

import javax.ws.rs.ext.Provider;

@Provider
@SuppressWarnings("serial")
public class UnauthorisedException extends RuntimeException {
    
    public UnauthorisedException() {
        super("This is an unauthorised action. It has been recorded and will be reported to the security team.");
    }
	
	public UnauthorisedException(String message) {
		super(message);
	}

}
