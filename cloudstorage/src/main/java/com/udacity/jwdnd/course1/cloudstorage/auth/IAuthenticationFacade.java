package com.udacity.jwdnd.course1.cloudstorage.auth;

import org.springframework.security.core.Authentication;

// https://www.baeldung.com/get-user-in-spring-security
public interface IAuthenticationFacade {
    Authentication getAuthentication();
    int getAuthenticatedUserId();
}
