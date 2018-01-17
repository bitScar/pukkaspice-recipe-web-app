package com.pukkaspice.web.service;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pukkaspice.web.common.exception.UserExistsException;
import com.pukkaspice.web.common.model.form.JoinForm;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.UserRole;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;
import com.pukkaspice.web.service.email.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class RegisteringMemberTest {
    
    @InjectMocks
    private RegistrationService registrationService;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private UserService userService;
    
    @Mock
    private EmailService emailService;
    
    
    @Test(expected=UserExistsException.class)
    public void should_fail_when_user_already_has_an_account() throws UserExistsException, MessagingException {
        JoinForm joinForm = createValidJoinForm();
        given(userMapper.isUserExists(joinForm.getEmailAddress())).willReturn(Boolean.TRUE);
        
        registrationService.registerMember(joinForm);
        
        fail("Expected UserExistsException to be thrown but it wasn't");
        
        verify(userMapper).isUserExists(joinForm.getEmailAddress()); // verifies that this happened at least once
    }
    
    @Test
    public void should_create_an_account_if_they_dont_have_one_with_40_char_hashed_password() throws UserExistsException, MessagingException {
        JoinForm joinForm = createValidJoinForm();

        given(userMapper.isUserExists(joinForm.getEmailAddress())).willReturn(Boolean.FALSE);
        
        registrationService.registerMember(joinForm);
        
        then(userMapper).should(times(1)).insertNewUserSummary(any(UserSummary.class), any(String.class));
        then(userService).should(times(1)).insertUserRole(any(UserSummary.class), anyListOf(UserRole.class));
        
        ArgumentCaptor<String> hashedPassword = ArgumentCaptor.forClass(String.class);
        verify(userMapper).insertNewUserSummary(any(UserSummary.class), hashedPassword.capture());
        
        assertEquals(40, hashedPassword.getValue().length());
    }
    
    @Test
    public void should_create_account_that_they_need_to_verify_with_36_char_verification_key() throws UserExistsException, MessagingException {
        JoinForm joinForm = createValidJoinForm();

        given(userMapper.isUserExists(joinForm.getEmailAddress())).willReturn(Boolean.FALSE);
        
        registrationService.registerMember(joinForm);
        
        then(userMapper).should(times(1)).insertEmailAddressVerificationRecord(anyInt(), anyString());
        then(userService).should(times(1)).insertUserRole(any(UserSummary.class), anyListOf(UserRole.class));
        
        ArgumentCaptor<String> verificationKeyCapture = ArgumentCaptor.forClass(String.class);
        verify(userMapper).insertEmailAddressVerificationRecord(anyInt(), verificationKeyCapture.capture());
        assertEquals(36, verificationKeyCapture.getValue().length());
    }
    
    @Test
    public void should_send_them_a_welcome_email_with_verification_instructions() throws UserExistsException, MessagingException {
        JoinForm joinForm = createValidJoinForm();

        given(userMapper.isUserExists(joinForm.getEmailAddress())).willReturn(Boolean.FALSE);
        
        registrationService.registerMember(joinForm);
        
        then(emailService).should(times(1)).sendNewRegistrationInstructions(any(UserSummary.class), anyString());
        then(userService).should(times(1)).insertUserRole(any(UserSummary.class), anyListOf(UserRole.class));
    }
    
    @Test
    public void should_create_new_account_then_verification_record_then_send_email_to_user_in_that_order() throws UserExistsException, MessagingException {
        InOrder inOrder = inOrder(userMapper, emailService);
        
        JoinForm joinForm = createValidJoinForm();

        given(userMapper.isUserExists(joinForm.getEmailAddress())).willReturn(Boolean.FALSE);
        
        registrationService.registerMember(joinForm);
        
        then(userMapper).should(inOrder, times(1)).insertNewUserSummary(any(UserSummary.class), anyString());
        then(emailService).should(inOrder, times(1)).sendNewRegistrationInstructions(any(UserSummary.class), anyString());
    }
    
    private JoinForm createValidJoinForm() {
        JoinForm joinForm = new JoinForm();
        joinForm.setFirstName("TestUser");
        joinForm.setSurname("TestSurname");
        joinForm.setEmailAddress("test@pukkuspice.com");
        joinForm.setPassword("TestPassword");
        
        return joinForm;
    }
    
}
