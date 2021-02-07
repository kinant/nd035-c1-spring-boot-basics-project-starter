package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialMapper credMapper;

    public CredentialService(UserService userService, EncryptionService encryptionService, CredentialMapper credMapper) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credMapper = credMapper;
    }

    public List<Credential> getCredentials(String username){
        Integer userId = userService.getUserId(username);

        if(userId != null){
            return credMapper.getCredsByUserId(userId);
        }

        return null;
    }

    public int createCredential(Credential cred, String username){
        Integer userId = userService.getUserId(username);

        if(userId != null){
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), encodedKey);

            cred.setKey(encodedKey);
            cred.setPassword(encryptedPassword);
            cred.setUserId(userId);

            return credMapper.addCredential(cred);
        }

        return -1;
    }

    public Credential getCredential(int credId){

        return credMapper.getCredential(credId);
    }

    public Integer updateCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credMapper.updateCredential(credential);
    }

    public Integer deleteCredential(Integer credId){
        return credMapper.deleteCredential(credId);
    }

}
