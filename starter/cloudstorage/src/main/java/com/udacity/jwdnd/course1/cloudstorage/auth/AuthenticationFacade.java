package com.udacity.jwdnd.course1.cloudstorage.auth;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public int getAuthenticatedUserId() {
        Authentication authentication = getAuthentication();
        String username = authentication.getName();
        return userService.getUserId(username);
    }
}
