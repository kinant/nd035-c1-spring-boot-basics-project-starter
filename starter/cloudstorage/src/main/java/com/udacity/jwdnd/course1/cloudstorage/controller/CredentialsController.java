package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home/creds")
public class CredentialsController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialServiceService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialServiceService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String credentialsView(@ModelAttribute("cred") Credential cred, Authentication authentication, Model model){
        System.out.println("===== GET CRED =========");

        String username = authentication.getName();
        model.addAttribute("credentials", credentialService.getCredentials(username));
        return "_creds";
    }

    @PostMapping()
    public String addOrUpdateCredential(@ModelAttribute("cred") Credential cred, Authentication authentication, Model model){

        System.out.println("===== (POST) CRED =========");
        System.out.println("CRED ID: " + cred.getCredentialid());

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
            this.credentialService.createCredential(c, authentication.getName());
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
            this.credentialService.updateCredential(c);
        }
        return "home";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam int credid){
        System.out.println("===== (GET) DELETE NOTE =========");
        System.out.println("Deleting credential with id: " + credid);
        credentialService.deleteCredential(credid);
        return "home";
    }
}
