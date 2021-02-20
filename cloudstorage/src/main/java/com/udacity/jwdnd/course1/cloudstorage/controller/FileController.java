package com.udacity.jwdnd.course1.cloudstorage.controller;

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

@Controller
@RequestMapping("/home/files")
// https://github.com/spring-guides/gs-uploading-files
public class FileController {

    // private final StorageService storageService;
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping()
    public String listUplodadedFiles(Model model){

        List<File> allFiles = fileService.getFilesByUser();

        for (File file:
             allFiles) {
            System.out.println(file.toString());
        }

        model.addAttribute("files", fileService.getFilesByUser());
        return "_files";
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int result = this.fileService.addFile(file);

            if(result == 1) {
                redirectAttributes.addFlashAttribute("successMessage", "File was uploaded!");
            } else if(result == 999) {
                redirectAttributes.addFlashAttribute("errorMessage", "File could not be uploaded. A file with that name already exists!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "File was not uploaded. Please try again!");
            }
            return "redirect:/result";
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error uploading the file. Please try again!");
            return "redirect:/result";
        }
    }


    // https://knowledge.udacity.com/questions/271629
    @GetMapping("/download")
    public ResponseEntity downloadFile(@RequestParam Integer fileid) {

        File file = fileService.getFileByFileId(fileid);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam Integer fileid, RedirectAttributes redirectAttributes){

        try {
            int result = this.fileService.deleteFile(fileid);

            if(result == 1) {
                redirectAttributes.addFlashAttribute("successMessage", "File was was deleted!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "File was not deleted. Please try again!");
            }
            return "redirect:/result";
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error deleting the file. Please try again!");
            return "redirect:/result";
        }
    }
}
