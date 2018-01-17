package com.pukkaspice.web.common.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.pukkaspice.web.common.model.user.UserSummary;

@Controller
public class SecurityController {
    
    public UserSummary getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSummary userSummary = (UserSummary) authentication.getPrincipal();
         
        return userSummary;
    }
    
    public boolean hasRole(UserRole userRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(userRole.toString())) {
                return true;
            }
        }
        
        return false;
    }
    
}
