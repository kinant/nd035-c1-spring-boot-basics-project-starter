package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Interface Mapper for the Credentials Table
 */
@Mapper
public interface CredentialMapper {

    /**
     * Selects all credentials by user id
     * @param userid    The user id to select by
     * @return          List of the credentials by that user
     */
    @Select("SELECT * FROM CREDENTIALS WHERE userid= #{userid}")
    List<Credential> getCredsByUserId(Integer userid);

    /**
     * Selects a credential by it's id
     * @param credid    The id of the credential to be selected
     * @return          The credential found
     */
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid= #{credid}")
    Credential getCredential(Integer credid);

    /**
     * Selects a credential by username and url (used for testing)
     * @param url           The url to select by
     * @param username      The username to select by
     * @return              The credential found
     */
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}" +
            " AND username = #{username}")
    Credential getCredentialByURLAndUsername(String url, String username);

    /**
     * Inserts new credential to DB
     * @param credential    The credential to be inserted
     * @return              # of rows addded
     */
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer addCredential(Credential credential);

    /**
     * Updates a credential in the DB
     * @param credential    The credential to be updated
     * @return              # of rows updated
     */
    @Update("UPDATE CREDENTIALS SET username = #{username}, password = #{password}, url = #{url}, key = #{key} " +
            "WHERE credentialid = #{credentialid}")
    Integer updateCredential(Credential credential);

    /**
     * Deletes a credential from the DB
     * @param credid        The ID of the credential to be deleted
     * @return              # of rows deleted
     */
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credid}")
    Integer deleteCredential(Integer credid);
}
