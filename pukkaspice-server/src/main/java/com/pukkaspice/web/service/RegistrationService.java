package com.pukkaspice.web.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.pukkaspice.web.common.exception.CatchableAppException;
import com.pukkaspice.web.common.exception.UserExistsException;
import com.pukkaspice.web.common.exception.VerificationException;
import com.pukkaspice.web.common.model.form.JoinForm;
import com.pukkaspice.web.common.model.form.PasswordResetForm;
import com.pukkaspice.web.common.model.user.PasswordResetWindow;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.model.user.VerificationRecord;
import com.pukkaspice.web.common.security.PasswordHasher;
import com.pukkaspice.web.common.security.UserRole;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;
import com.pukkaspice.web.service.email.EmailService;

@Service("RegistrationService")  
@Repository
public class RegistrationService {

    public static final String RESET_PASSWORD_URL = "http://www.pukkaspice.com/secure/resetpassword/";
    private static final int PASSWORD_RESET_DAYS = 2;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;
    

    @Transactional
    public UserSummary registerMember(JoinForm joinForm) throws UserExistsException, MessagingException {
        UserSummary userSummary = new UserSummary();
        userSummary.setForename(joinForm.getFirstName());
        userSummary.setSurname(joinForm.getSurname());
        userSummary.setEmailAddress(joinForm.getEmailAddress());
        
        validateUserDoesAlreadyHaveAccount(userSummary);
        
        String password = joinForm.getPassword();
        String hashedPassword = PasswordHasher.generateHash(password);
        
        userMapper.insertNewUserSummary(userSummary, hashedPassword);
        ArrayList<UserRole> userRoles = Lists.newArrayList(UserRole.ROLE_MEMBER);
        userService.insertUserRole(userSummary, userRoles);
        
        String verificationKey = createVerificationKey();
        userMapper.insertEmailAddressVerificationRecord(userSummary.getUserId(), verificationKey);
        
        String verificationUrl = "http://www.pukkaspice.com/verification/" + verificationKey;
        
        emailService.sendNewRegistrationInstructions(userSummary, verificationUrl);
        
        return userSummary;
    }
    
    private void validateUserDoesAlreadyHaveAccount(UserSummary userSummary) throws UserExistsException {
        Boolean userExists = userMapper.isUserExists(userSummary.getEmailAddress());
        if (userExists) {
            throw new UserExistsException();
        }
    }
    
    /**
     * @return 36 char, random string
     */
    private String createVerificationKey() {
        String verificationCode = UUID.randomUUID().toString();
        return verificationCode;
    }
    
    public void verifyUserEmailAddress(String verificationKey) throws VerificationException {
        VerificationRecord verificationRecord =  userMapper.getEmailAddressVerificationRecord(verificationKey);
        
        if (verificationRecord == null) {
            throw new VerificationException("Invalid verification key");
        } else if (verificationRecord.getVerificationDate() != null) {
            throw new VerificationException("Thanks, but you are already verified");
        } else {
            verificationRecord.setVerificationDate(new Date());
            userMapper.updateEmailAddressVerificationRecord(verificationRecord);
        }
    }
    
    public void requestPasswordReset(String emailAddress) throws UserExistsException, MessagingException {
        if (userMapper.isUserExists(emailAddress)) {
            String verificationKey = createVerificationKey();
            Date expirationDate = createExpirationDate();
            PasswordResetWindow passwordResetWindow = new PasswordResetWindow(emailAddress, verificationKey, expirationDate, null);
            userMapper.insertPasswordResetWindow(passwordResetWindow);
            
            String verificationUrl = RESET_PASSWORD_URL + verificationKey;
            
            emailService.sendPasswordResetInstructions(emailAddress, verificationUrl);
        } else {
            throw new UserExistsException("No member found with the email address you provided.");
        }
    }
    
    private Date createExpirationDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, PASSWORD_RESET_DAYS);
        return c.getTime();
    }
    
    public boolean isResetKeyValid(String resetKey) {
       PasswordResetWindow passwordResetWindow = userMapper.getPasswordResetWindow(resetKey);
       if(passwordResetWindow == null || isPasswordResetWindowExpired(passwordResetWindow) == true) {
           return false;
       } else {
           return true;
       }
    }
    
    private boolean isPasswordResetWindowExpired(PasswordResetWindow passwordResetWindow) {
        Calendar nowCal = Calendar.getInstance();
        Calendar expireCal = Calendar.getInstance();
        expireCal.setTime(passwordResetWindow.getExperationDate());
        if (nowCal.after(expireCal)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Transactional
    public void resetPassword(PasswordResetForm passwordResetForm) throws CatchableAppException {
        PasswordResetWindow passwordResetWindow = userMapper.getPasswordResetWindow(passwordResetForm.getResetKey());
        if(passwordResetWindow == null || isPasswordResetWindowExpired(passwordResetWindow) == true) {
            throw new CatchableAppException("Password reset time window has expired.");
        } else {
            String hashedPassword = PasswordHasher.generateHash(passwordResetForm.getPassword());
            userMapper.updateUserPassword(passwordResetForm.getEmailAddress(), hashedPassword);
            
            passwordResetWindow.setDateChanged(new Date());
            userMapper.updatePasswordResetWindow(passwordResetWindow);
        }
    }
    
}
