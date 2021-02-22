package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * Interface Mapper for the Users Table
 */
@Mapper
public interface UserMapper {

    /**
     * Selects a user by username
     * @param username      Username to select by
     * @return              User found
     */
    @Select("SELECT * FROM USERS WHERE username= #{username}")
    User getUser(String username);

    /**
     * Returns the user id of a given user
     * @param username      The username to select by
     * @return              The ID of the user found
     */
    @Select("SELECT userid FROM USERS WHERE username = #{username}")
    Integer getUserId(String username);

    /**
     * Inserts a new user to the DB
     * @param user      The user to be added
     * @return          # of rows added
     */
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) " +
            "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer addUser(User user);
}