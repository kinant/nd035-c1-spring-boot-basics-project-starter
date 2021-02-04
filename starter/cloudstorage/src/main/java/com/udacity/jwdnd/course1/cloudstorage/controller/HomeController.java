package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public String homeView(Model model){
        // model.addAttribute("activeTab", "files");
        return "home";
    }

    @GetMapping("{tab}")
    public String tab(@PathVariable String tab) {

        System.out.println("IN TAB GET MAPPING!");

        if(Arrays.asList("notes").contains(tab)){
            return "_" + tab;
        }

        return "home";
    }

}
