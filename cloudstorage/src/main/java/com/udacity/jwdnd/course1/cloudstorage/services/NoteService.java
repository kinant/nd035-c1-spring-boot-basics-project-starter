package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle note related stuff
 */
@Service
public class NoteService {

    // reference to the note mapper
    private final NoteMapper noteMapper;

    // reference to the authentication facade
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    // constructor
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Used to get list of notes by user
     * @return      List of notes by user
     */
    public List<Note> getNotes(){
        // get the user id
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            // get the notes from DB
            return noteMapper.getNotesByUserId(userId);
        }

        return null;
    }

    /**
     * Used to create a new note
     * @param note      The note to be created
     * @return          # of rows added in DB
     */
    public int createNote(Note note){
        // get the user ID
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){

            // set the user id for the note
            note.setUserid(userId);

            // add it to DB and return the result
            return noteMapper.addNote(note);
        }

        return -1;
    }

    /**
     * Used to update a note
     * @param note      Note to be updated
     * @return          # of rows updated in DB
     */
    public int updateNote(Note note){
        // set the user id for the note
        note.setUserid(authenticationFacade.getAuthenticatedUserId());

        // update the note in the DB, return the result
        return noteMapper.updateNote(note);
    }

    /**
     * Used to delete a note
     * @param noteId        Note ID to be deleted
     * @return              # of rows deleted in DB
     */
    public int deleteNote(Integer noteId){
        // delete the note and return the result
        return noteMapper.deleteNote(noteId);
    }
}
