package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This is a helper class to intercept my requests. I am only using it for testing so that I can
 * see the request URL (used in conjunction with GeneralRequestInterceptor)
 * As seen in: https://stackoverflow.com/questions/38360215/how-to-create-a-spring-interceptor-for-spring-restful-web-services
 */
@Configuration
public class GeneralMVCConfig implements WebMvcConfigurer {

    // reference to the General request interceptor
    @Autowired
    private GeneralRequestInterceptor generalRequestInterceptor;

    /**
     * This function adds interceptors to the Interceptor Registry
     * @param registry  The Interceptor Registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // add the custom general request interceptor
        registry.addInterceptor(generalRequestInterceptor);
    }
}
