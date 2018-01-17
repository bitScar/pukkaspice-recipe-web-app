package com.pukkaspice.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

    @RequestMapping(value = "/blogs/{blogName}")
    public String getRecipesByCategory(
        @PathVariable(value = "blogName") String blogName, Locale locale, Model model) {
        
        model.addAttribute("title", "Pakora Dipping Sauces");
        model.addAttribute("metaDescription", "Three pakora sauces that are easy to make, and really taste great. Your family and friends will all want the recipes once they have tasted these.");
        
        return "blog/pakora-sauce";
    }
    
}
