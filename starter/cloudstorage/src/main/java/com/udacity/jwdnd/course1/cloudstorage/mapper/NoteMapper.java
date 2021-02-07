package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId= #{userId}")
    public List<Note> getNotesByUserId(Integer useId);

    @Select("SELECT * FROM NOTES WHERE noteId= #{noteId}")
    public Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userId) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public Integer addNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} " +
            "WHERE noteId = #{noteId}")
    public Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    public Integer deleteNote(Integer noteId);
}
