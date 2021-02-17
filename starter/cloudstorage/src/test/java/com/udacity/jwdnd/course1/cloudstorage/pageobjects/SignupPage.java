package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submitSignup(){
        passwordField.submit();
    }

    public void inputNewUser(){
        firstNameField.sendKeys("John");
        lastNameField.sendKeys("Smith");
        usernameField.sendKeys("jsmith");
        passwordField.sendKeys("password123");
    }

    public String getSuccessMessage(){
        return successMessage.getText();
    }

}
