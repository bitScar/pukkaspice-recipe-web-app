package com.pukkaspice.web.service.email;

import javax.mail.MessagingException;

import com.pukkaspice.web.common.model.ContactMessage;
import com.pukkaspice.web.common.model.user.UserSummary;

public interface EmailService {
    
    public void sendContactMessage(ContactMessage contactMessage) throws MessagingException;

    public void sendNewRegistrationInstructions(UserSummary userSummary, String verificationUrl) throws MessagingException ;

    public void sendPasswordResetInstructions(String emailAddress, String verificationUrl) throws MessagingException ;

}