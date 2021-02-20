package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class NotesTab {
    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement titleInput;

    @FindBy(id = "note-description")
    private WebElement descriptionInput;

    @FindBy(id = "userTable")
    private WebElement notesTable;

    public NotesTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNote(){
        addNoteBtn.click();
    }

    public void inputNewNote(String title, String description){
        titleInput.clear();
        descriptionInput.clear();
        titleInput.sendKeys(title);
        descriptionInput.sendKeys(description);
    }

    public void submit(){
        descriptionInput.submit();
    }

    public List<NoteRow> getNoteRows(){
        List<NoteRow> noteRows = new ArrayList<>();
        List<WebElement> notesList = notesTable.findElements(By.name("note"));

        for(WebElement note: notesList){
            System.out.println("=========== PRINTING NOTE =============");
            WebElement titleElement = note.findElement(By.name("note-title"));
            WebElement descElement = note.findElement(By.name("note-description"));

            String title = titleElement.getAttribute("innerHTML");
            String description = descElement.getAttribute("innerHTML");

            System.out.println("Title: " + title);
            System.out.println("Description: " + description);

            NoteRow nRow = new NoteRow(title, description);
            noteRows.add(nRow);
        }

        return noteRows;
    }

    // https://knowledge.udacity.com/questions/346030
    public boolean checkNoteExists(String title, String description){
        boolean noteExists = false;
        List<NoteRow> noteRows = getNoteRows();

        for(NoteRow nRow: noteRows){
            if(nRow.getTitle().equals(title) && nRow.getDescription().equals(description)){
                noteExists = true;
            }
        }
        return noteExists;
    }

    public void clickEditNote(){

        List<WebElement> buttons = notesTable.findElements(By.tagName("button"));

        for(WebElement button: buttons){

            if(button.getTagName().equals("button") && button.getAttribute("innerHTML").equals("Edit")){
                button.click();
                break;
            }
        }
    }

    public void clickDeleteNote(){

        List<WebElement> notes = notesTable.findElements(By.tagName("a"));

        for(WebElement note: notes){

            if(note.getTagName().equals("a") && note.getAttribute("innerHTML").equals("Delete")){
                note.click();
                break;
            }
        }
    }

    public int getNumNotes(){
        List<NoteRow> rows = getNoteRows();
        return rows.size();
    }
}
