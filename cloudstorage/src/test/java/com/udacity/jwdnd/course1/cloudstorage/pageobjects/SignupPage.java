package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object that represents the signup page
 */
public class SignupPage {

    // form field for the first name
    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    // form field for the last name
    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    // form field for the username
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    // form field for the password
    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // submits the form
    public void submitSignup(){
        passwordField.submit();
    }

    /**
     * Inputs user details into form
     * @param first_name    The first name
     * @param last_name     The last name
     * @param username      The username
     * @param password      The password
     */
    public void inputNewUser(String first_name, String last_name, String username, String password){
        // populate form fields
        firstNameField.sendKeys(first_name);
        lastNameField.sendKeys(last_name);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }
}
