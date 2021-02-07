package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId= #{userId}")
    public List<Credential> getCredsByUserId(Integer useId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId= #{credId}")
    public Credential getCredential(Integer credId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username = #{username}, password = #{password}, url = #{url}, key = {#key} " +
            "WHERE credentialId = #{credentialId}")
    public Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credId}")
    public Integer deleteCredential(Integer credId);
}
