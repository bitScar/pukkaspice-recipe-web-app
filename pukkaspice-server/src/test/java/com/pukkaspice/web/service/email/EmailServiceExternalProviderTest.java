package com.pukkaspice.web.service.email;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.pukkaspice.web.common.model.ContactMessage;
import com.pukkaspice.web.common.model.user.UserSummary;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceExternalProviderTest {

    @InjectMocks
    private EmailServiceExternalProvider emailServiceExternalProvider;

    @Mock
    private JavaMailSenderImpl mailSender;
    
    @Mock
    private VelocityEngine velocityEngine;

    @Mock
    private MimeMessage mimeMessage;

    @Test
    public void test_contact_message_is_sent() throws MessagingException {
        given(mailSender.createMimeMessage()).willReturn(mimeMessage);

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setEmail("test@test.com");
        contactMessage.setName("Test Name");
        contactMessage.setMessage("Test Message");

        emailServiceExternalProvider.sendContactMessage(contactMessage);

        verify(mailSender).send(any(MimeMessage.class));
    }
    
    @Test
    public void test_new_registration_message_is_sent() throws MessagingException {
        given(mailSender.createMimeMessage()).willReturn(mimeMessage);

        UserSummary userSummary = new UserSummary();
        userSummary.setEmailAddress("test@test.com");
        
        String verificationUrl = "test-verification-url";

        emailServiceExternalProvider.sendNewRegistrationInstructions(userSummary, verificationUrl);

        verify(mailSender).send(any(MimeMessage.class));
    }
    
    @Test
    public void test_new_password_reset_instruction_message_is_sent() throws MessagingException {
        given(mailSender.createMimeMessage()).willReturn(mimeMessage);

        String emailAddress = "test@test.com";
        String verificationUrl = "test-verification-url";

        emailServiceExternalProvider.sendPasswordResetInstructions(emailAddress, verificationUrl);

        verify(mailSender).send(any(MimeMessage.class));
    }

}
