package com.pukkaspice.web.service.email;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pukkaspice.web.common.model.ContactMessage;
import com.pukkaspice.web.common.model.user.UserSummary;

@Component
@Profile("EMAIL_DEV")
public class EmailServiceStub implements EmailService {
    
    private static Logger logger = Logger.getLogger(EmailServiceStub.class);

    public void sendContactMessage(ContactMessage contactMessage) throws MessagingException {
        logger.info("Sending stub contact message");
    }

    public void sendNewRegistrationInstructions(UserSummary userSummary, String verificationUrl) throws MessagingException {
        logger.info("Sending stub new registration instructions message");
    }

    public void sendPasswordResetInstructions(String emailAddress, String verificationUrl) throws MessagingException  {
        logger.info("Sending stub password reset instructions message");
    }

}
