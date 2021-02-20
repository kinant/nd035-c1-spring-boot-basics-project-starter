package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid= #{userid}")
    public List<Credential> getCredsByUserId(Integer userid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid= #{credid}")
    public Credential getCredential(Integer credid);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}" +
            " AND username = #{username}")
    public Credential getCredentialByURLAndUsername(String url, String username);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username = #{username}, password = #{password}, url = #{url}, key = #{key} " +
            "WHERE credentialid = #{credentialid}")
    public Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credid}")
    public Integer deleteCredential(Integer credid);
}
