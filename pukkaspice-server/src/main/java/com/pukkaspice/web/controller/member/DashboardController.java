package com.pukkaspice.web.controller.member;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pukkaspice.web.common.security.SecurityController;

@Controller
@RequestMapping(value = "/secure/member")
public class DashboardController {
    
    @Autowired
    private SecurityController securityController;

    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "/member/dashboard";
    }

}
