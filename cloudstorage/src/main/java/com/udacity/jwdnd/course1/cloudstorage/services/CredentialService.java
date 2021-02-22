package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * Service class to handle credential related stuff
 */
@Service
public class CredentialService {

    // references to encryption service and the credential mapper
    private final EncryptionService encryptionService;
    private final CredentialMapper credMapper;

    // reference to the authentication facade
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    // constructor
    public CredentialService(EncryptionService encryptionService, CredentialMapper credMapper) {
        this.encryptionService = encryptionService;
        this.credMapper = credMapper;
    }

    /**
     * Gets all the credentials for a user
     * @return List of credentials for that user
     */
    public List<Credential> getCredentials(){

        // get the user id
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        // check if not null
        if(userId != null){
            // get list from DB
            return credMapper.getCredsByUserId(userId);
        }
        return null;
    }

    /**
     * Returns a secure (Encrypted password) credential
     * @param credential    The credential to secure
     * @return              The secured credential
     */
    private Credential getSecureCredential(Credential credential){
        // get random bytes
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        // get the encoded key and encrypted password
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        // set the credential key and password (encrypted)
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credential;
    }

    /**
     * For creating a credential
     * @param credential        The credential to be added to DB
     * @return                  # of rows added by DB
     */
    public int createCredential(Credential credential){
        // get the user id
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            // secure the credential
            Credential secureCredential = getSecureCredential(credential);

            // set the user id
            secureCredential.setUserid(userId);

            // add the credential to the DB
            return credMapper.addCredential(secureCredential);
        }

        // return -1 if credential not added
        return -1;
    }

    /**
     * Used to get a specific credential
     * @param credentialId      ID of credential to get from DB
     * @return                  The credential found
     */
    public Credential getCredential(int credentialId){
        // get the credential from the DB
        return credMapper.getCredential(credentialId);
    }

    /**
     * Used to update a credential
     * @param credential    Credential to be updated
     * @return              # of rows updated by DB
     */
    public Integer updateCredential(Credential credential){
        // get old credential from DB
        Credential old_credential = getCredential(credential.getCredentialid());

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
        return credMapper.updateCredential(credential);
    }

    /**
     * Gets a credential by url and username
     * Used for testing
     * @param url           URL to search by
     * @param username      Username to search by
     * @return              The credential found
     */
    public Credential getCredentialByURLAndUserName(String url, String username){
        Credential c = credMapper.getCredentialByURLAndUsername(url, username);
        return c;
    }

    /**
     * Used to delete a credential
     * @param credId    The ID of the credential to be deleted
     * @return          # of rows deleted in DB
     */
    public Integer deleteCredential(Integer credId){
        return credMapper.deleteCredential(credId);
    }
}
