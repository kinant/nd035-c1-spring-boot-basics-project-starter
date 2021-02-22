package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(){
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){

        String signupErr = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
            signupErr = "Username already exists.";
        }

        if(signupErr == null){
            int rowsAdded = userService.createUser(user);

            if(rowsAdded < 0){
                signupErr = "There was an error signing you up. Please try again.";
            }
        }

        if(signupErr == null){
            // model.addAttribute("signupSuccess", true);
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.SUCCESS_SIGNUP_COMPLETE);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupErr);
        }

        return "signup";
    }

}
