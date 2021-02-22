package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

/**
 * Used to represent a row of a note (for testing)
 * Mapped to HTML elements
 */
public class NoteRow {
    private String title;
    private String description;

    public NoteRow(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
