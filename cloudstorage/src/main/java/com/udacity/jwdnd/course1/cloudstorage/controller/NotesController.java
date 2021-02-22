package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/notes")
public class NotesController {

    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
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
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_CREATE);
                } else {
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_CREATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_CREATE_UNKNOWN);
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
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_UPDATE);
                } else {
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_UPDATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_UPDATE_UNKNOWN);
                return "redirect:/result";
            }
        }
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam int noteid, RedirectAttributes redirectAttributes){
        try {
            int result = noteService.deleteNote(noteid);

            if(result == 1){
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_DELETE);
            } else {
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_DELETE);
            }
            return "redirect:/result";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_DELETE_UNKNOWN);
            return "redirect:/result";
        }
    }
}
