package com.udacity.jwdnd.course1.cloudstorage.auth;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class is used to get user authentication details.
 * Using this method so it is accessible to entire application
 * As seen in: https://www.baeldung.com/get-user-in-spring-security
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    // access to user service
    @Autowired
    private UserService userService;

    /**
     * Gets the authentication.
     * @return  returns the Authentication
     */
    @Override
    public Authentication getAuthentication() {
        // return authentication
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Increment a value by delta and return the new value.
     * @return returns the authenticated user's id
     */
    @Override
    public int getAuthenticatedUserId() {
        // get the authentication
        Authentication authentication = getAuthentication();

        // get the username
        String username = authentication.getName();

        // return the userId from DB (using userService)
        return userService.getUserId(username);
    }
}
