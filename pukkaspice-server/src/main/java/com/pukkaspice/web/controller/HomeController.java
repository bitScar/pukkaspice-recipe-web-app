package com.pukkaspice.web.controller;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pukkaspice.web.common.model.ContactMessage;
import com.pukkaspice.web.common.model.ContactMessageValidator;
import com.pukkaspice.web.service.email.EmailService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ContactMessageValidator contactMessageValidator;
	
	/**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Locale locale, Model model) {
        model.addAttribute("active", "home");
        return "home";
    }

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Locale locale, Model model) {
		
		model.addAttribute("active", "contact");
		
		ContactMessage contactMessage = new ContactMessage();
		model.addAttribute("contactForm", contactMessage);

		return "contact";
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String contact(@ModelAttribute("contactForm") ContactMessage contactMessage, BindingResult result, Model model, RedirectAttributes redir) throws MessagingException {
	    contactMessageValidator.validate(contactMessage, result);
	    
	    if (result.hasErrors()) {
	        return "contact";
	    }
	    
		emailService.sendContactMessage(contactMessage);
		redir.addFlashAttribute("formSubmitted", true);
		return "redirect:contact";
	}

}
