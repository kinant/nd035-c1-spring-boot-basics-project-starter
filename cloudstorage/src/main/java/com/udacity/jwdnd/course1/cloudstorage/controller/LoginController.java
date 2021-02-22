package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling all the requests for Login related stuff
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    // default login view
    @GetMapping()
    public String loginView(){
        return "login";
    }
}
