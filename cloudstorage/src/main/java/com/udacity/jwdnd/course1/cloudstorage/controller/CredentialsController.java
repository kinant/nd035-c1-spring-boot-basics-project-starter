package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
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
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_CREATE);
                } else {
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_CREATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_CREATE_UNKNOWN);
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
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_UPDATE);
                } else {
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_UPDATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_UPDATE_UNKNOWN);
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam int credid, RedirectAttributes redirectAttributes){
        try {
            int result = credentialService.deleteCredential(credid);

            if(result == 1){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_DELETE);
            } else {
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_DELETE);
            }
            return "redirect:/result";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_DELETE_UNKNOWN);
            return "redirect:/result";
        }
    }
}