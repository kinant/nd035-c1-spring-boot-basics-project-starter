package com.udacity.jwdnd.course1.cloudstorage.auth;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
