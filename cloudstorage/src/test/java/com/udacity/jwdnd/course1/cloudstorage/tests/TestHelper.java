package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import org.openqa.selenium.WebDriver;

/**
 * Helper class for our tests
 */
public class TestHelper {

    // test data
    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "password123";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";

    // logs in a user
    public static void login(WebDriver driver, int port){
        // go to the login page
        driver.get("http://localhost:" + port + "/login");

        // get reference to login page object
        LoginPage loginPage = new LoginPage(driver);

        // input details and submit
        loginPage.inputLoginCredentials(USERNAME, PASSWORD);
        loginPage.submit();
    }

    // signs up a user
    public static void signup(WebDriver driver, int port){
        // go to the
        driver.get("http://localhost:" + port + "/signup");

        // get reference to signup page object
        SignupPage signupPage = new SignupPage(driver);

        // input data
        signupPage.inputNewUser(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        // submit
        signupPage.submitSignup();
    }

    // goes to the home page
    public static void goHome(WebDriver driver, int port){
        driver.get("http://localhost:" + port + "/home");
    }
}
