package com.pukkaspice.web.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.service.UserService;

@Component(value = "PukkaAuthenticationProvider")
public class PukkaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String emailAddress = authentication.getName();
        String password = authentication.getCredentials().toString();
        password = PasswordHasher.generateHash(password);

        UserSummary userSummary = userService.getUserByEmailAddress(emailAddress, password);

        if (userSummary == null) {
            throw new BadCredentialsException("Your email address or password was incorrect. Please try again or reset your password with the link provided below.");
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        List<UserRole> userRoles = userService.getUserRoles(userSummary);
        for (UserRole r : userRoles) {
            grantedAuths.add(new SimpleGrantedAuthority(r.toString()));
        }

        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(userSummary, authentication.getCredentials(), grantedAuths);

        return authenticatedUser;
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    

}
