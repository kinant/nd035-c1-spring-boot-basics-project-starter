package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "notes-link")
    public WebElement notesLink;

    @FindBy(id = "creds-link")
    public WebElement credsLink;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToNotesTab(){
        notesLink.click();
    }

    public void goToCredsTab(){
        credsLink.click();
    }
}
