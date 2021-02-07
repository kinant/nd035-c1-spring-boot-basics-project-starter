package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(String username){
        Integer userId = userService.getUserId(username);

        if(userId != null){
            return noteMapper.getNotesByUserId(userId);
        }

        return null;
    }

    public int createNote(Note note, String username){
        Integer userId = userService.getUserId(username);

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
        return noteMapper.updateNote(note);
    }

    public Integer deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }

}
