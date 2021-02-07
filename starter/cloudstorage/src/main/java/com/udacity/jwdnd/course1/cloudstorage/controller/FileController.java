package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/files")
// https://github.com/spring-guides/gs-uploading-files
public class FileController {

    private final StorageService storageService;
    private final UserService userService;

    @Autowired
    public FileController(StorageService storageService, UserService userService){
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping()
    public String listUplodadedFiles(Model model, Authentication authentication){

        System.out.println("ATTEMPTING TO GET LIST OF USER FIES: ");

        Integer userId = userService.getUserId(authentication.getName());

        return "_files";
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        System.out.println("ATTEMPTING TO UPLOAD FILE: ");
        System.out.println("ATTEMPTING TO UPLOAD FILE: " + file.getName());

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        System.out.println("FINISH TO UPLOAD FILE: " + file.getName());
        return "home";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }




}
