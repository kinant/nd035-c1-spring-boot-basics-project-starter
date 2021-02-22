package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public String homeView(){
        return "home";
    }

    // https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-tabs
    @GetMapping("*")
    public String tab(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        // Extract uri from request
        // https://stackoverflow.com/questions/10072284/requestmapping-with-multiple-values-with-pathvariable-spring-3-0/10072643
        String uri = request.getRequestURI();
        String tab = uri.replace("/home/", "");

        if(Arrays.asList("files", "notes", "creds").contains(tab)){
            return "_" + tab;
        } else {
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.ERROR_PAGE_NOT_FOUND);
            return "redirect:/error";
        }
    }
}
