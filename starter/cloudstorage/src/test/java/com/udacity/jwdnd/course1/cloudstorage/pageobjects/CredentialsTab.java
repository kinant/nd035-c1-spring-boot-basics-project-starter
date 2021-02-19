package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CredentialsTab {
    @FindBy(id = "add-creds-btn")
    WebElement addCredBtn;

    @FindBy(id = "credential-url")
    WebElement urlInput;

    @FindBy(id = "credential-username")
    WebElement usernameInput;

    @FindBy(id = "credential-password")
    WebElement passwordInput;

    @FindBy(id = "credentialTable")
    WebElement credentialTable;

    public CredentialsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addCredential(){
        addCredBtn.click();
    }

    public void inputNewCredential(String url, String username, String password){
        urlInput.clear();
        usernameInput.clear();
        passwordInput.clear();

        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submit(){
        passwordInput.submit();
    }

    // https://knowledge.udacity.com/questions/347576
    public boolean checkCredentialCreated(String url, String username, String password){
        boolean urlExists = false;
        boolean userExists = false;
        boolean passwordExists = false;



        List<WebElement> credentials = credentialTable.findElements(By.tagName("th"));
        credentials.addAll(credentialTable.findElements(By.tagName("td")));

        for(WebElement cred: credentials){
            if(cred.getAttribute("innerHTML").equals(url)){
                urlExists = true;
            }
            if(cred.getAttribute("innerHTML").equals(username)){
                userExists = true;
            }
        }

        return urlExists && userExists;
    }

//    public void clickEditCredential(){
//
//        List<WebElement> notes = notesTable.findElements(By.tagName("button"));
//
//        for(WebElement note: notes){
//            System.out.println("Note element tag: " + note.getTagName());
//            System.out.println("Note element inner html: " + note.getAttribute("innerHTML"));
//            if(note.getTagName().equals("button") && note.getAttribute("innerHTML").equals("Edit")){
//                note.click();
//                break;
//            }
//        }
//    }

//    public void clickDeleteNote(){
//
//        List<WebElement> notes = notesTable.findElements(By.tagName("a"));
//
//        for(WebElement note: notes){
//            System.out.println("Note element tag: " + note.getTagName());
//            System.out.println("Note element inner html: " + note.getAttribute("innerHTML"));
//            if(note.getTagName().equals("a") && note.getAttribute("innerHTML").equals("Delete")){
//                note.click();
//                break;
//            }
//        }
//    }

//    public int getNumNotes(){
//        List<WebElement> notes = notesTable.findElements(By.id("note"));
//        System.out.println("NUM NOTES: " + notes.size());
//        return notes.size();
//    }
}
