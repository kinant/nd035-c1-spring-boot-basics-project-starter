package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/creds")
public class CredentialsController {

    private final CredentialService credentialService;

    public CredentialsController(CredentialService credentialServiceService) {
        this.credentialService = credentialServiceService;
    }

    @GetMapping()
    public String credentialsView(@ModelAttribute("cred") Credential cred, Model model){
        model.addAttribute("credentials", credentialService.getCredentials());
        return "_creds";
    }

    @PostMapping()
    public String addOrUpdateCredential(@ModelAttribute("cred") Credential cred, RedirectAttributes redirectAttributes){

        if(cred.getCredentialid() == null) {
            // create new note
            Credential c = new Credential(
                    null,
                    cred.getUrl(),
                    null,
                    cred.getUsername(),
                    cred.getPassword(),
                    null
            );

            try {
                int result = this.credentialService.createCredential(c);

                if(result == 1){
                    redirectAttributes.addFlashAttribute("successMessage", "Credential was created!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Credential was not created. Please try again!");
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute("errorMessage", "There was an creating the credential. Please try again!");
                return "redirect:/result";
            }
        } else {
            // update note
            Credential c = new Credential(
                    cred.getCredentialid(),
                    cred.getUrl(),
                    null,
                    cred.getUsername(),
                    cred.getPassword(),
                    null
            );

            try {
                int result = this.credentialService.updateCredential(c);

                if(result == 1){
                    redirectAttributes.addFlashAttribute("successMessage", "Credential was updated!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Credential was not updated. Please try again!");
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute("errorMessage", "There was an error updating the credential. Please try again!");
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam int credid, RedirectAttributes redirectAttributes){
        try {
            int result = credentialService.deleteCredential(credid);

            if(result == 1){
                redirectAttributes.addFlashAttribute("successMessage", "Credential was deleted!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Credential was not deleted. Please try again!");
            }
            return "redirect:/result";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error deleting the credential. Please try again!");
            return "redirect:/result";
        }
    }
}