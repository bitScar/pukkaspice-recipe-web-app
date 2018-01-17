package com.pukkaspice.web.service.email;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.pukkaspice.web.common.model.ContactMessage;
import com.pukkaspice.web.common.model.user.UserSummary;

@Component
@Profile("EMAIL_PROD")
public class EmailServiceExternalProvider implements EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityEngine velocityEngine;
    

    public void sendContactMessage(ContactMessage contactMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("PukkaSpice - Contact Page Message");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("codecrocodile@gmail.com");
        helper.setTo("codecrocodile@gmail.com");

        Map<String, Object> model = new HashMap<>();
        model.put("name", contactMessage.getName());
        model.put("email", contactMessage.getEmail());
        model.put("message", contactMessage.getMessage());
        String messageBody = mergeTemplateIntoString("com/pukkaspice/web/service/email/ContactMessageTemplate.vm", model); // VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, , "UTF-8", model);
        helper.setText(messageBody, true);
        
        mailSender.send(message);
    }

    public void sendNewRegistrationInstructions(UserSummary userSummary, String verificationUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("PukkaSpice - Confirm Email");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("codecrocodile@gmail.com");
        helper.setTo(userSummary.getEmailAddress());

        Map<String, Object> model = new HashMap<>();
        model.put("url", verificationUrl);
        
        StringWriter result = new StringWriter();
        VelocityContext velocityContext = new VelocityContext(model);
        velocityEngine.mergeTemplate("com/pukkaspice/web/service/email/NewRegistrationTemplate.vm", "UTF-8", velocityContext, result);
        
        String messageBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/pukkaspice/web/service/email/NewRegistrationTemplate.vm", "UTF-8", model);
        helper.setText(messageBody, true);
        mailSender.send(message);
    }

    public void sendPasswordResetInstructions(String emailAddress, String verificationUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("PukkaSpice - Reset Password");
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("codecrocodile@gmail.com");
        helper.setTo(emailAddress);

        Map<String, Object> model = new HashMap<>();
        model.put("url", verificationUrl);
        String messageBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/pukkaspice/web/service/email/PasswordResetTemplate.vm", "UTF-8", model);
        helper.setText(messageBody, true);
        mailSender.send(message);
    }
    
    /*
     * I was previously using VelocityEngineUtils.mergeTemplateIntoString(...) but this was difficult to test with.
     */
    private String mergeTemplateIntoString(String templateLocation, Map<String, Object> model) {
        StringWriter result = new StringWriter();
        VelocityContext velocityContext = new VelocityContext(model);
        velocityEngine.mergeTemplate(templateLocation, "UTF-8", velocityContext, result);
        return result.toString();
    }
    
}
