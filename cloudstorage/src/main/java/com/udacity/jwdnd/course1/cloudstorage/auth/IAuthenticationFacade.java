package com.udacity.jwdnd.course1.cloudstorage.auth;

import org.springframework.security.core.Authentication;

/**
 * This interface is used to get user authentication details.
 * Using this method so it is accessible to entire application
 * As seen in: https://www.baeldung.com/get-user-in-spring-security
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
    int getAuthenticatedUserId();
}
