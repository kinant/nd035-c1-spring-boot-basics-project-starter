package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.core.annotation.AnnotationUtils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Custom class to handle exceptions
 * As seen in: https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * Handle the Max Upload Size Exceeded Exception
     * @return      Model And View representing the error page
     */
    @ResponseStatus()
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceededException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_SIZE_TOO_BIG);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    /**
     * Handle all other exceptions
     * @param e     The exception
     * @return      Model And View representing the error page
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        ModelAndView mav = new ModelAndView();
        mav.addObject(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_SERVER_ERROR);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}

