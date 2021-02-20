package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String tab(HttpServletRequest request) {

        // Extract uri from request
        // https://stackoverflow.com/questions/10072284/requestmapping-with-multiple-values-with-pathvariable-spring-3-0/10072643
        String uri = request.getRequestURI();
        String tab = uri.replace("/home/", "");

        if(Arrays.asList("files", "notes", "creds").contains(tab)){
            return "_" + tab;
        }

        return "empty";
    }
}
