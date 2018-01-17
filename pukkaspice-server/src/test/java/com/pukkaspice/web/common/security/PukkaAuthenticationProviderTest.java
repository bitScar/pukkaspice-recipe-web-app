package com.pukkaspice.web.common.security;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class PukkaAuthenticationProviderTest {

    @InjectMocks
    private PukkaAuthenticationProvider pukkaAuthenticationProvider;
    
    @Mock
    private UserService userService;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    
    @Test
    public void should_return_username_and_password_authentication_token_if_user_is_found() {
        given(userService.getUserByEmailAddress(anyString(), anyString())).willReturn(new UserSummary());
        
        List<UserRole> userRoles = new ArrayList<>();
        given(userService.getUserRoles(Matchers.any(UserSummary.class))).willReturn(userRoles);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken("", "");  
        Authentication authenticate = pukkaAuthenticationProvider.authenticate(authentication);
        
        assertThat(authenticate, instanceOf(UsernamePasswordAuthenticationToken.class));
    }
    
    @Test
    public void should_return_authentication_token_with_user_roles_when_user_has_them() {
        given(userService.getUserByEmailAddress(anyString(), anyString())).willReturn(new UserSummary());
        
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.ROLE_MEMBER);
        userRoles.add(UserRole.ROLE_ADMIN);
        given(userService.getUserRoles(Matchers.any(UserSummary.class))).willReturn(userRoles);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken("", "");  
        Authentication authenticate = pukkaAuthenticationProvider.authenticate(authentication);
        
        assertEquals(authenticate.getAuthorities().size(), 2);
    }
    
    @Test
    public void should_throw_BadCredentialsException_if_username_and_password_not_found() {
        given(userService.getUserByEmailAddress(anyString(), anyString())).willReturn(null);
        
        thrown.expect(BadCredentialsException.class);
        thrown.expectMessage("Your email address or password was incorrect. Please try again or reset your password with the link provided below.");
        
        Authentication authentication = new UsernamePasswordAuthenticationToken("", "");  
        pukkaAuthenticationProvider.authenticate(authentication);
    }
    
    @Test
    public void should_only_accept_user_password_authentication_type() {
        assertTrue(pukkaAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
        assertFalse(pukkaAuthenticationProvider.supports(TestingAuthenticationToken.class));
    }
    
}
