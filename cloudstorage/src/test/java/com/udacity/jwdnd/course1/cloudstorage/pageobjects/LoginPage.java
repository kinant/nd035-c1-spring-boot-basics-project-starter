package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import com.udacity.jwdnd.course1.cloudstorage.helpers.MessageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object that represents the login page
 */
public class LoginPage {
    // the username input in the form
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    // the password input in the form
    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "success-msg")
    private WebElement signupSuccessMessage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Inputs username and password into the form
     * @param username  Username to input
     * @param password  Password to input
     */
    public void inputLoginCredentials(String username, String password){
        // input the username and password into the form fields
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }

    // submits the form
    public void submit(){
        passwordField.submit();
    }

    // checks success message from the HTML page
    public boolean checkSuccessMessage(){
        // check that the correct signup success message exists and is shown
        return signupSuccessMessage.getText().equals(MessageHelper.SUCCESS_SIGNUP_COMPLETE);
    }
}
