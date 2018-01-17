package com.pukkaspice.web.common.model;

import org.apache.commons.validator.routines.EmailValidator;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContactMessageValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ContactMessage.class.equals(clazz);
    }

    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty", "Name field required");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty", "Email field required");
        ValidationUtils.rejectIfEmpty(errors, "message", "message.empty", "Message field required");
        
        ContactMessage cm = (ContactMessage) object;
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(cm.getEmail()) == false) {
            errors.rejectValue("email", "email.invalid", "Email is invalid");
        }
        
        if (cm.getName().length() > 50 || cm.getEmail().length() > 50 || cm.getMessage().length() > 500) {
            errors.reject("field.toolong", "One of the field values are too long.");
        }
        
        cm.setName(Encode.forHtmlContent(cm.getName()));
        cm.setEmail(Encode.forHtmlContent(cm.getEmail()));
        cm.setMessage(Encode.forHtmlContent(cm.getMessage()));
    }

}
