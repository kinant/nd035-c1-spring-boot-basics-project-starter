package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid= #{userid}")
    public List<Note> getNotesByUserId(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid= #{noteid}")
    public Note getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userId) " +
            "VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    public Integer addNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "WHERE noteId = #{noteid}")
    public Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public Integer deleteNote(Integer noteid);
}