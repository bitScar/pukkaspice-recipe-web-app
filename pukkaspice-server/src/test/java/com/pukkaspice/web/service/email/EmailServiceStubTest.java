package com.pukkaspice.web.service.email;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

public class EmailServiceStubTest {
    
    private EmailServiceStub emailServiceStub;
    
    @Before
    public void setUp() {
        emailServiceStub = new EmailServiceStub();
    }
    
    @Test
    public void testSubMethods() throws MessagingException {
        emailServiceStub.sendContactMessage(null);
        emailServiceStub.sendNewRegistrationInstructions(null, null);
        emailServiceStub.sendPasswordResetInstructions(null, null);
    }

}
