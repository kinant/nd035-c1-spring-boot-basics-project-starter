package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a helper class to intercept my requests. I am only using it for testing so that I can
 * see the request URL
 * As seen in: https://stackoverflow.com/questions/38360215/how-to-create-a-spring-interceptor-for-spring-restful-web-services
 */
@Component
public class GeneralRequestInterceptor extends HandlerInterceptorAdapter {

    /**
     * Pre handle a request.
     * @param request   The HTTP Servlet Request
     * @param response  The HTTP Servlet Response
     * @param handler   The HTTP Servlet
     * @return          Returns true by default
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // just print out the request URL
        System.out.println("REQUEST: " + request.getRequestURL().toString());
        return true;
    }
}
