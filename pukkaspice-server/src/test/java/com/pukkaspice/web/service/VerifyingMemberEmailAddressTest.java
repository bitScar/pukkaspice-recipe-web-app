package com.pukkaspice.web.service;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.pukkaspice.web.common.exception.VerificationException;
import com.pukkaspice.web.common.model.user.VerificationRecord;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;

@RunWith(MockitoJUnitRunner.class)
public class VerifyingMemberEmailAddressTest {
    
    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserMapper userMapper;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    @Test
    public void should_fail_if_verification_key_does_not_exist() throws VerificationException {
        String invalidVerificationKey = "testVerificationKey";
        
        given(userMapper.getEmailAddressVerificationRecord(invalidVerificationKey)).willReturn(null);
        thrown.expect(VerificationException.class);
        thrown.expectMessage("Invalid verification key");
        
        registrationService.verifyUserEmailAddress(invalidVerificationKey);
    }
    
    @Test
    public void should_fail_if_user_is_already_verified() throws VerificationException {
        String validVerificationKey = "testVerificationKey";
        
        VerificationRecord verificationRecord = new VerificationRecord();
        verificationRecord.setUserId(1);
        verificationRecord.setKey(validVerificationKey);
        verificationRecord.setVerificationDate(new Date());
        
        given(userMapper.getEmailAddressVerificationRecord(validVerificationKey)).willReturn(verificationRecord);
        thrown.expect(VerificationException.class);
        thrown.expectMessage("Thanks, but you are already verified");
        
        registrationService.verifyUserEmailAddress(validVerificationKey);
    }
    
    @Test
    public void should_update_verification_date_as_current_date_if_verification_key_valid() throws VerificationException {
        String validVerificationKey = "testVerificationKey";
        
        VerificationRecord verificationRecord = new VerificationRecord();
        verificationRecord.setUserId(1);
        verificationRecord.setKey(validVerificationKey);
        verificationRecord.setVerificationDate(null);
        
        given(userMapper.getEmailAddressVerificationRecord(validVerificationKey)).willReturn(verificationRecord);
        
        registrationService.verifyUserEmailAddress(validVerificationKey);
        
        then(userMapper).should(times(1)).updateEmailAddressVerificationRecord(verificationRecord);
        
        ArgumentCaptor<VerificationRecord> verificationRecordCaptor = ArgumentCaptor.forClass(VerificationRecord.class);
        verify(userMapper).updateEmailAddressVerificationRecord(verificationRecordCaptor.capture());
        assertNotNull("Expected verification date to be set when everything is valid", verificationRecordCaptor.getValue().getVerificationDate());
    }
    
}
