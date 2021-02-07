package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    public String listUplodadedFiles(Model model, Authentication authentication){
        System.out.println("ATTEMPTING TO GET LIST OF USER FIES: ");

        List<File> allFiles = fileService.getFilesByUser(authentication.getName());

        for (File file:
             allFiles) {
            System.out.println(file.toString());
        }

        model.addAttribute("files", fileService.getFilesByUser(authentication.getName()));



        return "_files";
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Authentication authentication) {

        System.out.println("ATTEMPTING TO UPLOAD FILE: ");
        System.out.println("File name (original): " + file.getOriginalFilename());
        System.out.println("File name: " + file.getName());
        System.out.println("File size: " + file.getSize());
        System.out.println("Content type " + file.getContentType());

        // storageService.store(file);
        // redirectAttributes.addFlashAttribute("message",
        //        "You successfully uploaded " + file.getOriginalFilename() + "!");
        //
        try {
            this.fileService.addFile(file, authentication.getName());
        } catch (IOException e){
            e.printStackTrace();
        }
        return "home";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
