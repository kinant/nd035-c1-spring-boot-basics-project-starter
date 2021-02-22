package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class NotesTab {

    // the add note button
    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    // the title form input
    @FindBy(id = "note-title")
    private WebElement titleInput;

    // the description form input
    @FindBy(id = "note-description")
    private WebElement descriptionInput;

    // the table of notes
    @FindBy(id = "userTable")
    private WebElement notesTable;

    public NotesTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // clicks the add note button
    public void addNote(){
        addNoteBtn.click();
    }

    // inputs note data into form
    public void inputNewNote(String title, String description){
        // clear form
        titleInput.clear();
        descriptionInput.clear();

        // populate inputs
        titleInput.sendKeys(title);
        descriptionInput.sendKeys(description);
    }

    // submits the form
    public void submit(){
        descriptionInput.submit();
    }

    /**
     * Used to get all the note rows from the HTML page
     * @return  The list of note rows found
     */
    public List<NoteRow> getNoteRows(){
        List<NoteRow> noteRows = new ArrayList<>();

        // find all list of all the elements with name of note
        List<WebElement> notesList = notesTable.findElements(By.name("note"));

        // iterate over each element
        for(WebElement note: notesList){

            // extract information
            WebElement titleElement = note.findElement(By.name("note-title"));
            WebElement descElement = note.findElement(By.name("note-description"));

            String title = titleElement.getAttribute("innerHTML");
            String description = descElement.getAttribute("innerHTML");

            // create a new note row
            NoteRow nRow = new NoteRow(title, description);

            // add it to the list
            noteRows.add(nRow);
        }

        return noteRows;
    }

    /**
     * Checks if a note exists in the page table
     * @param title         Title of the note
     * @param description   Description of the ntoe
     * @return              True if found, false otherwise
     * Ref:                 https://knowledge.udacity.com/questions/346030
     */
    public boolean checkNoteExists(String title, String description){
        boolean noteExists = false;

        // Get all the rows of notes on the table
        List<NoteRow> noteRows = getNoteRows();

        // iterate over each note
        for(NoteRow nRow: noteRows){

            // check if a row with the required data exists
            if(nRow.getTitle().equals(title) && nRow.getDescription().equals(description)){
                noteExists = true;
            }
        }
        return noteExists;
    }

    // clicks the edit credentials button
    public void clickEditNote(){

        // find all buttons
        List<WebElement> buttons = notesTable.findElements(By.tagName("button"));

        // iterate over each button
        for(WebElement button: buttons){

            // find the edit button and click it
            if(button.getTagName().equals("button") && button.getAttribute("innerHTML").equals("Edit")){
                button.click();
                break;
            }
        }
    }

    // clicks the delete note button
    public void clickDeleteNote(){

        // find all <a> tag elements
        List<WebElement> notes = notesTable.findElements(By.tagName("a"));

        // iterate over each element
        for(WebElement note: notes){

            // find the delete button and click it
            if(note.getTagName().equals("a") && note.getAttribute("innerHTML").equals("Delete")){
                note.click();
                break;
            }
        }
    }

    // returns the number of note rows in the table
    public int getNumNotes(){
        List<NoteRow> rows = getNoteRows();
        return rows.size();
    }
}
