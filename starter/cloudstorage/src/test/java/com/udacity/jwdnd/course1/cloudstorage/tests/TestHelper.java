package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import org.openqa.selenium.WebDriver;

public class TestHelper {

    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "password123";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";

    public static void login(WebDriver driver, int port){
        driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputLoginCredentials(USERNAME, PASSWORD);
        loginPage.submit();
    }

    public static void signup(WebDriver driver, int port){
        driver.get("http://localhost:" + port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.inputNewUser(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
        signupPage.submitSignup();
    }
}
