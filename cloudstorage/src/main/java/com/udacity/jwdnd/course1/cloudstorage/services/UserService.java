package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Service class to handle user related stuff
 */
@Service
public class UserService {

    // references to the user mapper and hash service
    private final UserMapper userMapper;
    private final HashService hashService;

    // constructor
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Used to create a user
     * @param user      User to be created
     * @return          # of rows added in DB
     */
    public int createUser(User user){

        // Create random bytes
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Get encoded salt and the hashed password
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        // add the new user, return result
        return userMapper.addUser(new User(
                null,
                user.getUsername(),
                encodedSalt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName()
        ));
    }

    /**
     * Used to get user from DB
     * @param username      Username to select by
     * @return              The user found
     */
    public User getUser(String username){
        // get the user, return it
        return userMapper.getUser(username);
    }

    /**
     * Used to get the user id, given the username
     * @param username      Username to search by
     * @return              ID of the user found
     */
    public Integer getUserId(String username){
        // get user id from DB
        return userMapper.getUserId(username);
    }

    /**
     * Used to check if a username is available (ie. exists in DB)
     * @param username      Username to search for
     * @return              True if username available, false otherwise
     */
    public boolean isUsernameAvailable(String username){
        // returns true if user found in DB, false if not (user returned would be null)
        return userMapper.getUser(username) == null;
    }
}