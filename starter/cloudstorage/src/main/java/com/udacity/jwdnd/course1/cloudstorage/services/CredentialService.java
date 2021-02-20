package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialMapper credMapper;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public CredentialService(UserService userService, EncryptionService encryptionService, CredentialMapper credMapper) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credMapper = credMapper;
    }

    public List<Credential> getCredentials(){
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            return credMapper.getCredsByUserId(userId);
        }

        return null;
    }

    private Credential getSecureCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credential;
    }

    public int createCredential(Credential credential){
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            Credential secureCredential = getSecureCredential(credential);
            secureCredential.setUserid(userId);

            return credMapper.addCredential(secureCredential);
        }

        return -1;
    }

    public Credential getCredential(int credentialId){
        return credMapper.getCredential(credentialId);
    }

    public Integer updateCredential(Credential credential){
        // get old credential from DB
        Credential old_credential = getCredential(credential.getCredentialid());

        System.out.println("OLD CREDENTIAL: " + old_credential.toString());

        // check if password has changed (so we do not update unchanged passwords)
        String newPassword = credential.getPassword();
        String oldEncryptedPassword = old_credential.getPassword();
        String oldPassword = encryptionService.decryptValue(oldEncryptedPassword, old_credential.getKey());

        // if password has changed, then we generate a new secure encrypted one
        if(!newPassword.equals(oldPassword)){
            credential = getSecureCredential(credential);
        } else {
            credential.setKey(old_credential.getKey());
            credential.setPassword(old_credential.getPassword());
        }

        credential.setUserid(old_credential.getUserid());
        System.out.println("NEW CREDENTIAL: " + credential.toString());
        return credMapper.updateCredential(credential);
    }

    public Credential getCredentialByURLAndUserName(String url, String username){
        System.out.println("======== GETTING CREDENTIAL BY URL AND USERNAME ==============");
        System.out.println("url: " + url);
        System.out.println("username: " + username);

        Credential c = credMapper.getCredentialByURLAndUsername(url, username);
        System.out.println("Credential: " + c.toString());
        return c;
    }

    public Integer deleteCredential(Integer credId){
        return credMapper.deleteCredential(credId);
    }
}
