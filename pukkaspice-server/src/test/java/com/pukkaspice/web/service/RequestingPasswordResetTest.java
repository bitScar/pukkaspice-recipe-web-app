package com.pukkaspice.web.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.mail.MessagingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pukkaspice.web.common.exception.UserExistsException;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;
import com.pukkaspice.web.service.email.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class RequestingPasswordResetTest {

    @InjectMocks
    private RegistrationService registrationService;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private EmailService emailService;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    

    @Test
    public void should_fail_if_no_user_with_email_address_has_been_found() throws UserExistsException, MessagingException {
        String invalidEmailAddress = "test@pukkaspice.com";
        
        given(userMapper.isUserExists(invalidEmailAddress)).willReturn(Boolean.FALSE);
        thrown.expect(UserExistsException.class);
        thrown.expectMessage("No member found with the email address you provided.");
        
        registrationService.requestPasswordReset(invalidEmailAddress);

        then(userMapper).should(times(1)).isUserExists(invalidEmailAddress);
    }
    
    @Test
    public void should_send_email_to_user_email_address_with_36_char_verification_key_if_we_find_user_with_email_address() throws UserExistsException, MessagingException {
        String validEmailAddress = "test@pukkaspice.com";
        
        given(userMapper.isUserExists(validEmailAddress)).willReturn(Boolean.TRUE);
        
        registrationService.requestPasswordReset(validEmailAddress);

        ArgumentCaptor<String>  verificationKeyCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendPasswordResetInstructions(eq(validEmailAddress), verificationKeyCaptor.capture());
        assertEquals("Expected 36 char validation, but wasn't found.", verificationKeyCaptor.getValue().substring(RegistrationService.RESET_PASSWORD_URL.length()).length(), 36);
        
        InOrder inOrder = inOrder(userMapper, emailService);
        then(userMapper).should(inOrder, times(1)).isUserExists(validEmailAddress);
        then(emailService).should(inOrder, times(1)).sendPasswordResetInstructions(eq(validEmailAddress), anyString());
    }
    
}
