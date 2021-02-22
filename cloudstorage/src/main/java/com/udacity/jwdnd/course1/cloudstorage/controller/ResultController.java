package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for handling all the requests for the results page
 */
@Controller
@RequestMapping("/result")
public class ResultController {

    // default results page
    @GetMapping()
    public String getResultPage(){
        return "result";
    }
}
