package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public FileService(FileMapper fileMapper, UserService userService){
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> getFilesByUser(){
        Integer userid = authenticationFacade.getAuthenticatedUserId();
        return fileMapper.getFilesByUserId(userid);
    }

    public boolean checkFileExists(String filename){
        Integer userid = authenticationFacade.getAuthenticatedUserId();
        return fileMapper.getFileName(userid, filename) != null;
    }

    public int addFile(MultipartFile file) throws IOException {
        Integer userid = authenticationFacade.getAuthenticatedUserId();
        File f = new File (
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                String.valueOf(file.getSize()),
                userid,
                file.getBytes()
        );

        if(checkFileExists(file.getOriginalFilename())){
            return 999;
        }

        return fileMapper.addFile(f);
    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public File getFileByFileId(Integer fileid){
        return fileMapper.getFileByFileId(fileid);
    }
}
