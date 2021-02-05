package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public String homeView(Model model){
        // model.addAttribute("activeTab", "files");
        return "home";
    }

    // https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-tabs
    @GetMapping("*")
    public String tab(HttpServletRequest request) {

        // Extract uri from request
        // https://stackoverflow.com/questions/10072284/requestmapping-with-multiple-values-with-pathvariable-spring-3-0/10072643
        String uri = request.getRequestURI();
        String tab = uri.replace("/home/", "");

        //TODO: Remove comments.
        //System.out.println("===================================================");
        //System.out.println("IN TAB GET MAPPING! TAB: " + tab);

        if(Arrays.asList("files", "notes", "creds").contains(tab)){
            //System.out.println("RETURNING TAB!");
            //System.out.println("===================================================");

            return "_" + tab;
        }

        //System.out.println("===================================================");
        return "empty";
    }

}
