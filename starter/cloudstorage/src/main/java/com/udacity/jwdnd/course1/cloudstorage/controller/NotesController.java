package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/notes")
public class NotesController {

    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping()
    public String notesView(@ModelAttribute("newNote") NoteForm noteForm, Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("notes", noteService.getNotes(username));

        return "_notes";
    }

    @PostMapping()
    public String addNote(@ModelAttribute("newNote") NoteForm noteForm, Authentication authentication, Model model){

        System.out.println("===== (POST) NOTE =========");

        Note n = new Note(
                null,
                noteForm.getNotetitle(),
                noteForm.getNotedescription(),
                null
        );
        this.noteService.createNote(n, authentication.getName());
        return "home";
    }

}
