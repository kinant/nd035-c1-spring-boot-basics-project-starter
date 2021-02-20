package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String notesView(@ModelAttribute Note note, Model model){
        model.addAttribute("notes", noteService.getNotes());
        return "_notes";
    }

    @PostMapping()
    public String addOrUpdateNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes){

        if(note.getNoteid() == null) {
            // create new note
            Note n = new Note(
                    null,
                    note.getNotetitle(),
                    note.getNotedescription(),
                    null
            );

            try {
                int result = noteService.createNote(n);

                if(result == 1) {
                    redirectAttributes.addFlashAttribute("successMessage", "Note was added!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Note was not added. Please try again!");
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute("errorMessage", "There was an error adding the note. Please try again!");
                return "redirect:/result";
            }
        } else {
            // update note
            Note n = new Note(
                    note.getNoteid(),
                    note.getNotetitle(),
                    note.getNotedescription(),
                    null
            );

            try {
                int result = this.noteService.updateNote(n);

                if(result == 1){
                    redirectAttributes.addFlashAttribute("successMessage", "Note was updated!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Note was not updated. Please try again!");
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute("errorMessage", "There was an error updating the note. Please try again!");
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam int noteid, RedirectAttributes redirectAttributes){
        try {
            int result = noteService.deleteNote(noteid);

            if(result == 1){
                redirectAttributes.addFlashAttribute("successMessage", "Note was deleted!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Note was not deleted. Please try again!");
            }
            return "redirect:/result";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error deleting the note. Please try again!");
            return "redirect:/result";
        }
    }
}
