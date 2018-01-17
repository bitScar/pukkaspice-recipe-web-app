package com.pukkaspice.web.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pukkaspice.web.service.RegistrationService;

@Controller
public class LoginController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private Validator validator;
    
    
    @RequestMapping(value = "/secure/login", method = RequestMethod.GET)
    public String navigateToLogin(Locale locale, Model model) {
        return "/member/login";
    }
    
}
