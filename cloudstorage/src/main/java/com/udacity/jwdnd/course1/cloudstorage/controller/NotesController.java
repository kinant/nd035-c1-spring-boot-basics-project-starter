package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for handling all the requests for Notes related stuff
 */
@Controller
@RequestMapping("/home/notes")
public class NotesController {

    // reference to notes service
    private final NoteService noteService;

    // constructor
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Handles the GET request for the notes view page tab
     * @param note      Bind the method parameter to a named model
     * @param model     The model
     * @return          Returns the notes tab HTML
     */
    @GetMapping()
    public String notesView(@ModelAttribute Note note, Model model){
        model.addAttribute("notes", noteService.getNotes());
        return "_notes";
    }

    /**
     * Handles the POST Requests for adding or updating a note
     * @param note                  Bind the method parameter to a named model (note to be added)
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return                      Redirects to the results page
     */
    @PostMapping()
    public String addOrUpdateNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes){

        // check if the note id exists (for adding a new note)
        if(note.getNoteid() == null) {
            // create new note
            Note n = new Note(
                    null,
                    note.getNotetitle(),
                    note.getNotedescription(),
                    null
            );

            try {
                // create the note and store the result
                int result = noteService.createNote(n);

                if(result == 1) {
                    // success if result = 1
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_CREATE);
                } else {
                    // error otherwise
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_CREATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                // handle exceptions
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
                // update the note and store the result
                int result = this.noteService.updateNote(n);

                if(result == 1){
                    // success if result = 1
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_UPDATE);
                } else {
                    // error otherwise
                    redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_UPDATE);
                }
                return "redirect:/result";
            } catch(Exception e){
                // handle exceptions
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_UPDATE_UNKNOWN);
                return "redirect:/result";
            }
        }
    }

    /**
     * Handles the GET Requests for deleting a note
     * @param credid                ID of the note to be deleted
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return                      Redirects to the results page
     */
    @GetMapping("/delete")
    public String deleteNote(@RequestParam int noteid, RedirectAttributes redirectAttributes){
        try {
            // delete the note and store the result
            int result = noteService.deleteNote(noteid);

            if(result == 1){
                // success if result = 1
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.NOTE_SUCCESS_DELETE);
            } else {
                // error otherwise
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_DELETE);
            }
            return "redirect:/result";
        } catch (Exception e){
            // handle exceptions
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.NOTE_ERROR_DELETE_UNKNOWN);
            return "redirect:/result";
        }
    }
}