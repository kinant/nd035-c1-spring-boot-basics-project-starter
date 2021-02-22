package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Interface Mapper for the Notes Table
 */
@Mapper
public interface NoteMapper {

    /**
     * Selects all the notes by a user id
     * @param userid        User ID to select by
     * @return              List of notes by that user
     */
    @Select("SELECT * FROM NOTES WHERE userid= #{userid}")
    List<Note> getNotesByUserId(Integer userid);

    /**
     * Selects a note by it's note ID
     * @param noteid        The note ID to select by
     * @return              The note found
     */
    @Select("SELECT * FROM NOTES WHERE noteid= #{noteid}")
    Note getNote(Integer noteid);

    /**
     * Inserts a new note to the DB
     * @param note      The note to be added
     * @return          # of rows added
     */
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userId) " +
            "VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer addNote(Note note);

    /**
     * Updates a note in the DB
     * @param note      The note to be updated
     * @return          # of rows updated
     */
    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "WHERE noteId = #{noteid}")
    Integer updateNote(Note note);

    /**
     * Deletes a note by note id
     * @param noteid    The ID of the note to be deleted
     * @return          # of rows deleted
     */
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    Integer deleteNote(Integer noteid);
}