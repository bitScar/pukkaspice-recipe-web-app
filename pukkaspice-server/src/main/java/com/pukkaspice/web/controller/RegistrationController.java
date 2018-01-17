package com.pukkaspice.web.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pukkaspice.web.common.exception.CatchableAppException;
import com.pukkaspice.web.common.exception.UserExistsException;
import com.pukkaspice.web.common.exception.VerificationException;
import com.pukkaspice.web.common.model.form.JoinForm;
import com.pukkaspice.web.common.model.form.PasswordResetForm;
import com.pukkaspice.web.common.security.PukkaAuthenticationProvider;
import com.pukkaspice.web.service.RegistrationService;

@Controller
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private Validator validator;
    
    @Autowired
    private PukkaAuthenticationProvider pukkaAuthenticationProvider;
    

    @RequestMapping(value = "/secure/join", method = RequestMethod.GET)
    public String navigateToJoin(Locale locale, Model model) {
        JoinForm joinForm = new JoinForm();
        model.addAttribute("joinForm", joinForm);
        
        return "/member/join";
    }
    
    /**
     * Registers user for the first time, logs them in, and redirects them to their member page.
     */
    @RequestMapping(value = "/secure/join", method = RequestMethod.POST)
    public String join(@ModelAttribute("joinForm") @Valid JoinForm joinForm, BindingResult result, Model model, RedirectAttributes redir, HttpServletRequest request)  throws MessagingException {
        if (result.hasErrors()) {
            return "/member/join";
        } else {
            try {
                registrationService.registerMember(joinForm);
            } catch (UserExistsException e) {
                result.addError(new ObjectError("userRegisterdWithEmail", "You are already registered with this email address. We have sent you an email that will allow you to reset your password."));
                return "/member/join";
            }
            
            autoAuthenticate(request, joinForm);
            
            return "redirect:/secure/member/dashboard";
        }
    }
    
    private void autoAuthenticate(HttpServletRequest request, JoinForm joinForm) {
        UsernamePasswordAuthenticationToken authenticationRequestToken = new UsernamePasswordAuthenticationToken(joinForm.getEmailAddress(), joinForm.getPassword());
        UsernamePasswordAuthenticationToken authenticatedToken = (UsernamePasswordAuthenticationToken) pukkaAuthenticationProvider.authenticate(authenticationRequestToken);
        request.getSession();
        authenticatedToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
    }
    
    /**
     * Allows the user to verify their email address. Without doing this they won't be able to post anything
     * publicly, and their account will be deleted after a specified amount of time.
     */
    @RequestMapping(value = "/secure/verification/{verificationKey}", method = RequestMethod.GET)
    public String validateUserEmailAddress(@PathVariable String verificationKey, Locale locale, Model model, RedirectAttributes redir) {
        // TODO validate key 
        
        try {
            registrationService.verifyUserEmailAddress(verificationKey);
            model.addAttribute("verificationSucessfull", Boolean.TRUE);
        } catch (VerificationException e) {
            model.addAttribute("verificationSucessfull", Boolean.FALSE);
            model.addAttribute("verificationMessage", e.getMessage());
        }
        
        redir.addAttribute("emailVerified", true);
        return "redirect:/secure/member/dashboard";
    }
    
    /**
     * Gets the page to request a reset of password by entering their email address
     */
    @RequestMapping(value = "/secure/requestresetpassword", method = RequestMethod.GET)
    public String requestResetPassword(Model model, RedirectAttributes redir) {
        model.addAttribute("passwordResetForm", new PasswordResetForm());
        model.addAttribute("formConfig", "enterEmail");
        return "/member/password-reset";
    }
    
    /**
     * Request sent to click on password reset link
     */
    @RequestMapping(value = "/secure/requestresetpassword", method = RequestMethod.POST)
    public String requestResetPassword(@ModelAttribute("passwordResetForm") @Valid PasswordResetForm passwordResetForm, BindingResult result, Model model, RedirectAttributes redir) throws MessagingException {
        if (result.hasErrors() == false) {
            try {
                registrationService.requestPasswordReset(passwordResetForm.getEmailAddress());
            } catch (UserExistsException e) {
                result.addError(new ObjectError("passwordResetError", e.getMessage()));
            }
        } else {
            model.addAttribute("formConfig", "enterEmail");
            return "/member/password-reset";
        }
        
        return "/member/password-reset";
    }
    
    /**
     * When they click on the email link to reset their password this will give them the page if the window has not expired.
     */
    @RequestMapping(value = "/secure/resetpassword/{resetKey}", method = RequestMethod.GET)
    public String requestPasswordResetPage(@PathVariable String resetKey, Locale locale, Model model, RedirectAttributes reDir) {
        // TODO validate key 
        
        
        PasswordResetForm passwordResetForm = new PasswordResetForm();
        model.addAttribute("passwordResetForm", passwordResetForm);
        
        if (registrationService.isResetKeyValid(resetKey) == true) {
            passwordResetForm.setResetKey(resetKey);
            model.addAttribute("resetKeyValid", true);
            model.addAttribute("formConfig", "enterPassword");
            return "/member/password-reset";
        } else {
            model.addAttribute("resetKeyValid", false);
            model.addAttribute("formConfig", "enterEmail");
            return "/member/password-reset";
        }
    }
    
    /**
     */
    @RequestMapping(value = "/secure/resetpassword", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute("passwordResetForm") @Valid PasswordResetForm passwordResetForm, BindingResult result, Model model, RedirectAttributes redir) throws MessagingException {
        //TODO validate key and password properly
        
        if (result.hasErrors() == false) {
            try {
                registrationService.resetPassword(passwordResetForm);
            } catch (CatchableAppException e) {
                e.printStackTrace();
                
                model.addAttribute("resetKeyValid", false);
                model.addAttribute("formConfig", "enterEmail");
                return "/member/password-reset";
            }

            redir.addFlashAttribute("passwordReset", true);
            return "redirect:/secure/login";
        } else {
            model.addAttribute("resetKeyValid", false);
            model.addAttribute("formConfig", "enterEmail");
            return "/member/password-reset";
        }
        
    }
    
}
