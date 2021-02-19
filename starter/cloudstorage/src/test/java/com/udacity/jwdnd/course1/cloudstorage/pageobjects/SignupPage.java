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

    public void inputNewUser(String first_name, String last_name, String username, String password){
        firstNameField.sendKeys(first_name);
        lastNameField.sendKeys(last_name);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }

    public String getSuccessMessage(){
        return successMessage.getText();
    }

}
