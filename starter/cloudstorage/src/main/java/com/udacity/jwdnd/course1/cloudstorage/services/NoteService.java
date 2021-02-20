package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.AuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final UserService userService;
    private final NoteMapper noteMapper;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(String username){
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            return noteMapper.getNotesByUserId(userId);
        }

        return null;
    }

    public int createNote(Note note, String username){
        Integer userId = authenticationFacade.getAuthenticatedUserId();

        if(userId != null){
            note.setUserid(userId);
            return noteMapper.addNote(note);
        }

        return -1;
    }

    public Note getNote(int noteId){
        return noteMapper.getNote(noteId);
    }

    public Integer updateNote(Note note){
        note.setUserid(authenticationFacade.getAuthenticatedUserId());
        return noteMapper.updateNote(note);
    }

    public Integer deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }

}
