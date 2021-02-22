package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for handling all the requests for Credentials related stuff
 */
@Controller
@RequestMapping("/home/creds")
public class CredentialsController {

    // reference to the credentials service
    private final CredentialService credentialService;

    // constructor
    public CredentialsController(CredentialService credentialServiceService) {
        this.credentialService = credentialServiceService;
    }

    /**
     * Handles the GET request for the credentials view page tab
     * @param cred      Bind the method parameter to a named model
     * @param model     The model
     * @return          Returns the credentials tab HTML
     */
    @GetMapping()
    public String credentialsView(@ModelAttribute("cred") Credential cred, Model model){
        // add the attribute
        model.addAttribute("credentials", credentialService.getCredentials());

        // return the credentials tab page
        return "_creds";
    }

    /**
     * Handles the POST Requests for adding or updating a credential
     * @param cred                  Bind the method parameter to a named model (credential to be added)
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return                      Redirects to the results page
     */
    @PostMapping()
    public String addOrUpdateCredential(@ModelAttribute("cred") Credential cred, RedirectAttributes redirectAttributes){

        // check if a credential id exists (for adding a new credential)
        if(cred.getCredentialid() == null) {
            // create new credential
            Credential c = new Credential(
                    null,
                    cred.getUrl(),
                    null,
                    cred.getUsername(),
                    cred.getPassword(),
                    null
            );

            try {
                // add the credential and store the result
                int result = this.credentialService.createCredential(c);

                if(result == 1){
                    // if succeeded then result is 1
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_CREATE);
                }
                else {
                    // else there was an error...
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_CREATE);
                }
                // redirect
                return "redirect:/result";
            } catch(Exception e){
                // catch errors and redirect
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
                // update the credential and store the returned result
                int result = this.credentialService.updateCredential(c);

                if(result == 1){
                    // succeeds if result = 1
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_UPDATE);
                } else {
                    // fails otherwise...
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_UPDATE);
                }
                // redirect
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_UPDATE_UNKNOWN);
                return "redirect:/result";
            }
        }
    }

    /**
     * Handles the GET Requests for deleting a credential
     * @param credid                ID of the credential to be deleted
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return                      Redirects to the results page
     */
    @GetMapping("/delete")
    public String deleteCredential(@RequestParam int credid, RedirectAttributes redirectAttributes){
        try {
            // delete the credential and store the returned result
            int result = credentialService.deleteCredential(credid);

            if(result == 1){
                // succeeded if result = 1
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.CREDENTIAL_SUCCESS_DELETE);
            } else {
                // failed otherwise
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_DELETE);
            }
            return "redirect:/result";
        } catch (Exception e){
            // catch exceptions and redirect
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.CREDENTIAL_ERROR_DELETE_UNKNOWN);
            return "redirect:/result";
        }
    }
}