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

/**
 * Controller class for handling all the requests for the signup page
 */
@Controller
@RequestMapping("/signup")
public class SignupController {

    // reference to the user service
    private final UserService userService;

    // constructor
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    // default signup page
    @GetMapping()
    public String signupView(){
        return "signup";
    }

    /**
     * Handles POST request for creating a new user
     * @param user                  The user to be created
     * @param model                 The model
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return                      The signup page
     */
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){

        // for storing the error
        String signupErr = null;

        // check if the username does not exist
        if(!userService.isUsernameAvailable(user.getUsername())){
            signupErr = MessageHelper.ERROR_SIGNUP_USER_EXISTS;
        }

        // check if any errors
        if(signupErr == null){

            // create the user and store the result
            int rowsAdded = userService.createUser(user);


            if(rowsAdded < 0){
                // no rows added, so error
                signupErr = MessageHelper.ERROR_SIGNUP_ERROR;
            }
        }

        if(signupErr == null){
            // no errors, so user was added, redirect to login page
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.SUCCESS_SIGNUP_COMPLETE);
            return "redirect:/login";
        } else {
            // else, there was an error, show it
            model.addAttribute(MessageHelper.ATTR_SIGNUP_ERROR, signupErr);
        }

        return "signup";
    }

}
