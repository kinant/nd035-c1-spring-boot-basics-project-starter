package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//https://stackoverflow.com/questions/38360215/how-to-create-a-spring-interceptor-for-spring-restful-web-services
@Component
public class GeneralRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("REQUEST: " + request.getRequestURL().toString());
        return true;
    }
}
