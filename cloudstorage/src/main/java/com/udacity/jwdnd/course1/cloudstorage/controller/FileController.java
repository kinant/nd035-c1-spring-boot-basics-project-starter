package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller class for handling all the requests for Files related stuff
 */
@Controller
@RequestMapping("/home/files")
public class FileController {

    // private final StorageService storageService;
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    /**
     * Handle the GET requests to the default view (list of uploaded files)
     * @param model     The model
     * @return          The Files tab
     */
    @GetMapping()
    public String listUplodadedFiles(Model model){

        // get a list of all the files by the user
        List<File> allFiles = fileService.getFilesByUser();

        // adds them to the model
        model.addAttribute("files", fileService.getFilesByUser());
        return "_files";
    }

    /**
     * Handle the POST Request to upload a file
     * @param file                  The file to be uploaded
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *      *                       can use to select attributes for a redirect scenario
     * @return                      The results page
     */
    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            // add the file to DB and store the result
            int result = this.fileService.addFile(file);

            if(result == 1) {
                // success if result = 1
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_SUCCESS, MessageHelper.FILE_SUCCESS_CREATE);
            } else if(result == FileService.FILE_EXISTS_CODE) {
                // handle file exists error
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_EXISTS);
            } else if(result == FileService.FILE_NONE_SELECTED) {
                // handle no file selected error
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_NO_FILE);
            } else {
                // handle any other errors
                redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_CREATE);
            }
            // redirect
            return "redirect:/result";
        } catch(Exception e){
            // handle exceptions
            redirectAttributes.addFlashAttribute(MessageHelper.ATTR_ERROR, MessageHelper.FILE_ERROR_UNKNOWN);
            return "redirect:/result";
        }
    }

    /**
     * Handles the download of a file by the user
     * As seen in: https://knowledge.udacity.com/questions/271629
     * @param fileid    The ID of the file to be downloaded
     * @return          ResponseEntity (download the file)
     */
    @GetMapping("/download")
    public ResponseEntity downloadFile(@RequestParam Integer fileid) {

        File file = fileService.getFileByFileId(fileid);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }

    /**
     * Handles GET request to delete files
     * @param fileid                ID of file to be deleted
     * @param redirectAttributes    A specialization of the Model interface that controllers
     *                              can use to select attributes for a redirect scenario
     * @return
     */
    @GetMapping("/delete")
    public String deleteFile(@RequestParam Integer fileid, RedirectAttributes redirectAttributes){

        try {
            // delete the file and store the result
            int result = this.fileService.deleteFile(fileid);

            if(result == 1) {
                // success if result = 1
                redirectAttributes.addFlashAttribute("successMessage", "File was was deleted!");
            } else {
                // error otherwise
                redirectAttributes.addFlashAttribute("errorMessage", "File was not deleted. Please try again!");
            }
            // redirect
            return "redirect:/result";
        } catch(Exception e){
            // handle exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error deleting the file. Please try again!");
            return "redirect:/result";
        }
    }
}