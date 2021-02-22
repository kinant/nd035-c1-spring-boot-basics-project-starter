package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object that represents the home page
 */
public class HomePage {

    // the tab link for notes
    @FindBy(id = "notes-link")
    private WebElement notesLink;

    // the tab link for credentials
    @FindBy(id = "creds-link")
    private WebElement credsLink;

    // the logout button
    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // click the notes tab link
    public void goToNotesTab(){
        notesLink.click();
    }

    // click the credentials tab link
    public void goToCredsTab(){
        credsLink.click();
    }

    // click the logout button
    public void logout(){
        logoutBtn.click();
    }
}
