package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, filename, contenttype, filesize, userid, null AS filedata FROM FILES WHERE userid = #{userid}")
    public List<File> getFilesByUserId(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) "+
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public Integer deleteFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    public File getFileByFileId(Integer fileId);

}
