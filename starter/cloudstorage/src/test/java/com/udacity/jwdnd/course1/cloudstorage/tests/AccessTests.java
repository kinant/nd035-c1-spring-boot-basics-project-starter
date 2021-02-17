package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccessTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void unauthorizedAccessPages() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(1000);
		getSignupPage();
		Thread.sleep(1000);
		getLoginPage();
	}

	@Test
	public void SignupLoginLogout() throws InterruptedException {
		getSignupPage();
		SignupPage signupPage = new SignupPage(driver);
		signupPage.inputNewUser();
		signupPage.submitSignup();

		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getSuccessMessage());
		Thread.sleep(1000);
		Login();
	}

	public void Login() throws InterruptedException {
		getLoginPage();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.inputLoginCredentials();

		Thread.sleep(1000);

		loginPage.submit();
		getHomePage();
	}

	public void getHomePage(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
	}
}
