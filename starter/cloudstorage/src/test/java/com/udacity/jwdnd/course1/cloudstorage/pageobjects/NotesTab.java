package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotesTab {
    @FindBy(id = "add-note-btn")
    WebElement addNoteBtn;

    @FindBy(id = "note-title")
    WebElement titleInput;

    @FindBy(id = "note-description")
    WebElement descriptionInput;

    @FindBy(id = "userTable")
    WebElement notesTable;

    public NotesTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNote(){
        addNoteBtn.click();
    }

    public void inputNewNote(String title){
        titleInput.clear();
        descriptionInput.clear();
        titleInput.sendKeys(title);
        descriptionInput.sendKeys("This is note " + title);
    }

    public void submit(){
        descriptionInput.submit();
    }

    // https://knowledge.udacity.com/questions/346030
    public boolean checkNoteCreated(String title){
        boolean noteExists = false;

        List<WebElement> notes = notesTable.findElements(By.tagName("th"));

        for(WebElement note: notes){
            if(note.getAttribute("innerHTML").equals(title)){
                noteExists = true;
            }
        }

        return noteExists;
    }

    public void clickEditNote(){

        List<WebElement> notes = notesTable.findElements(By.tagName("button"));

        for(WebElement note: notes){
            System.out.println("Note element tag: " + note.getTagName());
            System.out.println("Note element inner html: " + note.getAttribute("innerHTML"));
            if(note.getTagName().equals("button") && note.getAttribute("innerHTML").equals("Edit")){
                note.click();
                break;
            }
        }
    }

    public void clickDeleteNote(){

        List<WebElement> notes = notesTable.findElements(By.tagName("a"));

        for(WebElement note: notes){
            System.out.println("Note element tag: " + note.getTagName());
            System.out.println("Note element inner html: " + note.getAttribute("innerHTML"));
            if(note.getTagName().equals("a") && note.getAttribute("innerHTML").equals("Delete")){
                note.click();
                break;
            }
        }
    }

    public int getNumNotes(){
        List<WebElement> notes = notesTable.findElements(By.id("note"));
        System.out.println("NUM NOTES: " + notes.size());
        return notes.size();
    }
}
