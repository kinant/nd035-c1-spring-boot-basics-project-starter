package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * This is a custom class to handle requests to the error page.
 * Using a custom error page rather than the default white list
 * As seen in: https://www.baeldung.com/spring-boot-custom-error-page
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * Handle the requests for the error page
     * @param request       The HTTP Servlet Request
     * @param model         The Model
     * @return              The error page
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        // get the status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // check if it is not null
        if (status != null) {

            // get the integer value of the status code
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle not found
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_NOT_FOUND);
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle internal server error
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_SERVER_ERROR);
            }
            else {
                // handle any other errors
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_UNKNOWN);
            }
        } else {
            // handle case if status code is null
            model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_UNKNOWN);
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}