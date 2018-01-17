package com.pukkaspice.web.service;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pukkaspice.web.common.exception.CatchableAppException;
import com.pukkaspice.web.common.model.form.PasswordResetForm;
import com.pukkaspice.web.common.model.user.PasswordResetWindow;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;
import com.pukkaspice.web.service.email.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class ResettingPasswordTest {
    
    @InjectMocks
    private RegistrationService registrationService;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private EmailService emailService;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    @Test
    public void should_fail_to_validate_reset_key_if_it_does_not_exist() {
        String invalidResetKey = "invlaidResetKey";
        given(userMapper.getPasswordResetWindow(invalidResetKey)).willReturn(null);
        
        boolean resetKeyValid = registrationService.isResetKeyValid(invalidResetKey);
        
        then(userMapper).should(times(1)).getPasswordResetWindow(invalidResetKey);
        assertFalse("Expected resetKey to be invalid as no password reset window was found.", resetKeyValid);
    }

    @Test
    public void should_fail_to_validate_reset_key_if_password_reset_window_closed() {
        String validResetKey = "validResetKey";
        PasswordResetWindow invalidPasswordResetWindow = createInvalidPasswordResetWindow(validResetKey);
        given(userMapper.getPasswordResetWindow(validResetKey)).willReturn(invalidPasswordResetWindow);

        boolean resetKeyValid = registrationService.isResetKeyValid(validResetKey);
        
        then(userMapper).should(times(1)).getPasswordResetWindow(validResetKey);
        assertFalse("Expected resetKey to be invalid as no password reset window was closed.", resetKeyValid);
    }
    
    private PasswordResetWindow createInvalidPasswordResetWindow(String resetKey) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.DATE, -1);
        PasswordResetWindow passwordResetWindow = new PasswordResetWindow("", resetKey, nowCal.getTime(), null);
        return passwordResetWindow;
    }
    
    private PasswordResetWindow createValidPasswordResetWindow(String resetKey) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.DATE, 1);
        PasswordResetWindow passwordResetWindow = new PasswordResetWindow("", resetKey, nowCal.getTime(), null);
        return passwordResetWindow;
    }
    
    @Test
    public void should_fail_if_validation_key_is_invalid() throws CatchableAppException {
        String invalidResetKey = "invlaidResetKey";
        given(userMapper.getPasswordResetWindow(invalidResetKey)).willReturn(null);
        
        thrown.expect(CatchableAppException.class);
        thrown.expectMessage("Password reset time window has expired.");
        
        PasswordResetForm passwordResetForm = createPasswordResetForm(invalidResetKey);
        registrationService.resetPassword(passwordResetForm);
        
        then(userMapper).should().getPasswordResetWindow(invalidResetKey);
    }
    
    private PasswordResetForm createPasswordResetForm(String resetKey) {
        PasswordResetForm passwordResetForm = new PasswordResetForm();
        passwordResetForm.setEmailAddress("test@pukkaspice.com");
        passwordResetForm.setPassword("testPassword");
        passwordResetForm.setResetKey(resetKey);
        return passwordResetForm;
    }
    
    @Test
    public void should_fail_if_password_reset_window_is_closed() throws CatchableAppException {
        String validResetKey = "validResetKey";
        PasswordResetForm passwordResetForm = createPasswordResetForm(validResetKey);
        PasswordResetWindow invalidPasswordResetWindow = createInvalidPasswordResetWindow(validResetKey);
        
        given(userMapper.getPasswordResetWindow(validResetKey)).willReturn(invalidPasswordResetWindow);
        
        
        thrown.expect(CatchableAppException.class);
        thrown.expectMessage("Password reset time window has expired.");
        
        registrationService.resetPassword(passwordResetForm);
        
        then(userMapper).should().getPasswordResetWindow(validResetKey);
        
    }
    
    @Test
    public void should_update_users_36_char_hashed_password_if_validation_key_is_valid_and_within_password_reset_window() throws CatchableAppException {
        String validResetKey = "validResetKey";
        PasswordResetForm passwordResetForm = createPasswordResetForm(validResetKey);
        PasswordResetWindow validPasswordResetWindow = createValidPasswordResetWindow(validResetKey);
        
        given(userMapper.getPasswordResetWindow(validResetKey)).willReturn(validPasswordResetWindow);
        
        registrationService.resetPassword(passwordResetForm);
        
        then(userMapper).should().updateUserPassword(eq(passwordResetForm.getEmailAddress()), anyString());
        then(userMapper).should().updatePasswordResetWindow(any(PasswordResetWindow.class));
    }
    

}
