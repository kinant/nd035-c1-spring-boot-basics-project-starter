package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    public List<File> getFilesByUserId(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, userid, filedata) "+
            "VALUES (#{filename}, #{contenttype}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public Integer addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = {#fileId}")
    public Integer deleteFile(Integer fileId);
}
