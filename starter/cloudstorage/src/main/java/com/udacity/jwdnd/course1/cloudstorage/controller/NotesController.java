package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home/notes")
public class NotesController {

    private final NoteService noteService;
    private final UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public String notesView(@ModelAttribute("newNote") NoteForm noteForm, Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("notes", noteService.getNotes(username));

        return "_notes";
    }

    @PostMapping()
    public String addNote(@ModelAttribute("newNote") Note note, Authentication authentication, Model model){

        System.out.println("===== (POST) NOTE =========");
        System.out.println("NOTE ID: " + note.getNoteId());

        if(noteForm.getNoteid() == null) {
            // create new note
            Note n = new Note(
                    null,
                    note.getNoteTitle(),
                    note.getNoteDescription(),
                    null
            );
            this.noteService.createNote(n, authentication.getName());
        } else {
            // update note
            Note n = new Note(
                    note.getNoteId(),
                    note.getNoteTitle(),
                    note.getNoteDescription(),
                    userService.getUserId(authentication.getName())
            );
            this.noteService.updateNote(n);
        }
        return "home";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam int noteid){
        System.out.println("===== (GET) DELETE NOTE =========");
        System.out.println("Deleting note with id: " + noteid);
        noteService.deleteNote(noteid);
        return "home";
    }
}
