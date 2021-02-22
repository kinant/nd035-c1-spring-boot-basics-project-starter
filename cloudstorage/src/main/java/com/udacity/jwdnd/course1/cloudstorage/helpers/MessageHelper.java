package com.udacity.jwdnd.course1.cloudstorage.helpers;

/**
 * Helper class is just used to provide all messages that are sent to the user
 */
public class MessageHelper {

    // attribute names
    public static final String ATTR_ERROR = "errorMessage";
    public static final String ATTR_SUCCESS = "successMessage";
    public static final String ATTR_SIGNUP_ERROR = "signupError";

    // error page errors
    public static final String ERROR_PAGE_NOT_FOUND = "Requested page not found! (404)";
    public static final String ERROR_PAGE_SERVER_ERROR = "There was an internal server error! (500)";
    public static final String ERROR_PAGE_UNKNOWN = "An unknown error occurred!";

    // signup page errors
    public static final String ERROR_SIGNUP_USER_EXISTS = "Username already exists";
    public static final String ERROR_SIGNUP_ERROR = "There was an error signing you up. Please try again.";
    public static final String SUCCESS_SIGNUP_COMPLETE = "Successfully signed up! Please proceed to log in:";

    // credential messages
    public static final String CREDENTIAL_SUCCESS_CREATE = "Credential was created!";
    public static final String CREDENTIAL_ERROR_CREATE = "Credential was not created. Please try again!";
    public static final String CREDENTIAL_ERROR_CREATE_UNKNOWN = "There was an error creating the credential. Please try again!";
    public static final String CREDENTIAL_SUCCESS_UPDATE = "Credential was updated!";
    public static final String CREDENTIAL_ERROR_UPDATE = "There was an error updating the credential! Please try again!";
    public static final String CREDENTIAL_ERROR_UPDATE_UNKNOWN = "There was an error updating the credential. Please try again!";
    public static final String CREDENTIAL_SUCCESS_DELETE = "Credential was deleted!";
    public static final String CREDENTIAL_ERROR_DELETE = "Credential was not deleted. Please try again!";
    public static final String CREDENTIAL_ERROR_DELETE_UNKNOWN = "There was an error deleting the credential. Please try again!";

    // notes messages
    public static final String NOTE_SUCCESS_CREATE = "Note was created!";
    public static final String NOTE_ERROR_CREATE = "Note was not created. Please try again!";
    public static final String NOTE_ERROR_CREATE_UNKNOWN = "There was an error creating the note. Please try again!";
    public static final String NOTE_SUCCESS_UPDATE = "Note was updated!";
    public static final String NOTE_ERROR_UPDATE = "There was an error updating the note! Please try again!";
    public static final String NOTE_ERROR_UPDATE_UNKNOWN = "There was an error updating the credential. Please try again!";
    public static final String NOTE_SUCCESS_DELETE = "Note was deleted!";
    public static final String NOTE_ERROR_DELETE = "Note was not deleted. Please try again!";
    public static final String NOTE_ERROR_DELETE_UNKNOWN = "There was an error deleting the note. Please try again!";

    // files messages
    public static final String FILE_SUCCESS_CREATE = "File was uploaded!";
    public static final String FILE_ERROR_CREATE = "File was not uploaded. Please try again!";
    public static final String FILE_ERROR_UNKNOWN = "There was an error uploading the file. Please try again!";
    public static final String FILE_ERROR_NO_FILE = "No file was selected! Please try again!";
    public static final String FILE_ERROR_SIZE_TOO_BIG = "File size exceeds maximum! Please try again!";
    public static final String FILE_ERROR_EXISTS = "A File with that name already exists! Please try again!";
}
