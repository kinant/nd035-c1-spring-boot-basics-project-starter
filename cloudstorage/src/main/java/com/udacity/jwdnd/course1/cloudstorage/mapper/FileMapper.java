package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Interface Mapper for the Files Table
 */
@Mapper
public interface FileMapper {

    /**
     * Selects all files by userid
     * @param userid        The ID of the user to select by
     * @return              List of files by the user
     */
    @Select("SELECT fileId, filename, contenttype, filesize, userid, null AS filedata FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserId(Integer userid);

    /**
     * Selects a file by a file id
     * @param fileId        The file ID to select by
     * @return              The file found
     */
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileByFileId(Integer fileId);

    /**
     * Selects a file by user id and name
     * Used to check if a file by a given name exists
     * @param userid        User ID to select by
     * @param filename      Filename to select by
     * @return              File found
     */
    @Select("SELECT filename FROM FILES WHERE userid=#{userid} AND filename=#{filename}")
    String getFileName(Integer userid, String filename);

    /**
     * Inserts new file into DB
     * @param file      The file to be inserted
     * @return          # of rows added
     */
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) "+
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFile(File file);

    /**
     * Deletes a file from the DB
     * @param fileId        ID of the file to be deleted
     * @return              # of rows deleted
     */
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer deleteFile(Integer fileId);

}
