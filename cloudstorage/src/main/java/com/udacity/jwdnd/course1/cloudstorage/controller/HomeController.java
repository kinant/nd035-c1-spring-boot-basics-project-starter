package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Controller for handling requests to the Home Page
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    // default home view
    @GetMapping()
    public String homeView(){
        return "home";
    }

    /**
     * Handles all GET requests of /home/*
     * Used to handle the clicking of the tabs
     * As seen in: https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-tabs
     * @param request               The HTTP Servlet Request
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *      *                       can use to select attributes for a redirect scenario
     * @return                      The selected tab or error page if not found (for invalid pages)
     */
    @GetMapping("*")
    public String tab(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        // Extract uri from request
        // https://stackoverflow.com/questions/10072284/requestmapping-with-multiple-values-with-pathvariable-spring-3-0/10072643
        String uri = request.getRequestURI();
        String tab = uri.replace("/home/", "");

        // check if it is any of the tabs
        if(Arrays.asList("files", "notes", "creds").contains(tab)){
            // return the tab
            return "_" + tab;
        } else {
            // redirect to error page otherwise
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_NOT_FOUND);
            return "redirect:/error";
        }
    }
}
