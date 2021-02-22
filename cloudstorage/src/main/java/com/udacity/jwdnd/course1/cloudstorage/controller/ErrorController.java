package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// https://www.baeldung.com/spring-boot-custom-error-page
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_NOT_FOUND);
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_SERVER_ERROR);
            } else {
                model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_UNKNOWN);
            }
        } else {
            model.addAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_UNKNOWN);
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}