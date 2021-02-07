package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteid = noteId;
        this.notetitle = noteTitle;
        this.notedescription = noteDescription;
        this.userid = userId;
    }

    public Integer getNoteId() {
        return noteid;
    }

    public void setNoteId(Integer noteId) {
        this.noteid = noteId;
    }

    public String getNoteTitle() {
        return notetitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.notetitle = noteTitle;
    }

    public String getNoteDescription() {
        return notedescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.notedescription = noteDescription;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userId) {
        this.userid = userId;
    }
}
