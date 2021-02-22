package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.core.annotation.AnnotationUtils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;


// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

//    @Override
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//
//        if(e.getClass() == MaxUploadSizeExceededException.class){
//            System.out.println("DO SOMETHING HERE!");
//            ModelAndView mav = new ModelAndView("result.html");
//            mav.addObject(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_SIZE_TOO_BIG);
//            return mav;
//        } else {
//            ModelAndView mav = new ModelAndView("error.html");
//            mav.addObject(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_SERVER_ERROR);
//            return mav;
//        }
//    }


    @ResponseStatus()
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceededException() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_SIZE_TOO_BIG);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

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

