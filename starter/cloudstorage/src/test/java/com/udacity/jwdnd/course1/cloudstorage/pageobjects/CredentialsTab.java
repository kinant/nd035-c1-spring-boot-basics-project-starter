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
}
