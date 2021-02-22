package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class configures the Spring Boot Security features of my application
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // reference to the custom authentication service
    private AuthenticationService authenticationService;

    // constructor
    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Configures application to use my custom authentication service
     * @param auth      Spring Boot Application Authentication Manager Builder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // set the authentication provider to my custom authentication service
        auth.authenticationProvider(this.authenticationService);
    }

    /**
     * Confuguration to make sure that any request is authenticated (to those pages that we wish so).
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // the following are set so as to enable the use of the H2 console
        // wasn't working otherwise
        // https://stackoverflow.com/questions/53395200/h2-console-is-not-showing-in-browser/53399807#53399807
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        // set the pages that need to be authenticated
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/h2-console/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        // set the login page
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        // set the default success url after login
        http.formLogin()
                .defaultSuccessUrl("/home", true);

        // set the logout url (default)
        http.logout()
                .logoutUrl("/logout");
    }
}
