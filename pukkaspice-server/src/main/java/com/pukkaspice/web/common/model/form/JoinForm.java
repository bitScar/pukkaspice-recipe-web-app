package com.pukkaspice.web.common.model.form;

import javax.validation.constraints.Size;

public class JoinForm extends LoginForm {
    
    @Size(min=2, max=50, message="Invalid first name length")
    private String firstName;
    
    @Size(min=2, max=50, message="Invalid surname length")
    private String surname;

    
    public JoinForm() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
