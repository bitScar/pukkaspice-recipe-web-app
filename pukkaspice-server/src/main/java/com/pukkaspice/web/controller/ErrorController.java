package com.pukkaspice.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    
    public static final String DEFAULT_ERROR_VIEW = "error-custom";
    
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView error(HttpServletRequest req) {
        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("errorCode", "404");
        mav.addObject("errorMessage", "Sorry, but this page does not exist.");
        mav.setViewName(DEFAULT_ERROR_VIEW);

        return mav;
    }
    
}
