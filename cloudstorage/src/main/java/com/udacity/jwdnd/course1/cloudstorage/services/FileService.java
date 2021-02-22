package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service class to handle file related stuff
 */
@Service
public class FileService {

    // reference to file mapper
    private final FileMapper fileMapper;

    // codes for returning errors
    public static final int FILE_EXISTS_CODE = 991;
    public static final int FILE_NONE_SELECTED = 992;

    // reference to the authentication facade
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    // constructor
    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    /**
     * Gets the list of files for a user
     * @return      The list of files for the authenticated user
     */
    public List<File> getFilesByUser(){
        // get the user id
        Integer userid = authenticationFacade.getAuthenticatedUserId();

        // get the list of files from DB
        return fileMapper.getFilesByUserId(userid);
    }

    /**
     * Checks if a file exists
     * @param filename      Filename to search
     * @return              Returns true if file exists, false if not
     */
    public boolean checkFileExists(String filename){
        // get the user id
        Integer userid = authenticationFacade.getAuthenticatedUserId();

        // check if the file exists in DB and return result
        return fileMapper.getFileName(userid, filename) != null;
    }

    /**
     * Used to add a file to the DB
     * @param file      The file to be added
     * @return          # of rows added in DB
     * @throws IOException
     */
    public int addFile(MultipartFile file) throws IOException {

        // get the user id
        Integer userid = authenticationFacade.getAuthenticatedUserId();

        // check if a file was selected (by file size)
        if(file.getSize() == 0){
            // return error code
            return FILE_NONE_SELECTED;
        }

        // check that the file does not exist
        if(checkFileExists(file.getOriginalFilename())){
            // return error code
            return FILE_EXISTS_CODE;
        }

        // create a new file
        File f = new File (
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                String.valueOf(file.getSize()),
                userid,
                file.getBytes()
        );

        // add file to the DB
        return fileMapper.addFile(f);
    }

    /**
     * Used to delete a file from the DB
     * @param fileId        ID of file to be deleted
     * @return              # of rows deleted
     */
    public int deleteFile(Integer fileId){
        // delete the file, return result
        return fileMapper.deleteFile(fileId);
    }

    /**
     * Used to get a file, given the file ID
     * @param fileid        File ID to search for
     * @return              The file found
     */
    public File getFileByFileId(Integer fileid){
        // get the file from DB (by file id)
        return fileMapper.getFileByFileId(fileid);
    }
}