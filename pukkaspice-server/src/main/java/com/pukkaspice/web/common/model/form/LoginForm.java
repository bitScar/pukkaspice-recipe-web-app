package com.pukkaspice.web.common.model.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class LoginForm {

    @Size(min=2, max=50, message="Invalid email address")
    @Email(message="Invalid email address")
    private String emailAddress;

    @Size(min=2, max=50, message="Invalid password length")
    private String password;
    
    
    public LoginForm() {}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
