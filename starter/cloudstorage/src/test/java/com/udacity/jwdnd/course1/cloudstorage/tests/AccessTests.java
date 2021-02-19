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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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


	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
	}

	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
	}

	public void getHomePage(){
		driver.get("http://localhost:" + this.port + "/home");
	}

	@Test
	@Order(1)
	public void getLoginPageTest(){
		getLoginPage();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getSingupPageTest(){
		getSignupPage();
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void unauthorizedAccessPages() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(1000);
		getSignupPage();
		Assertions.assertEquals("Sign Up", driver.getTitle());
		Thread.sleep(1000);
		getLoginPage();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void SignupLoginLogout() throws InterruptedException {
		getSignupPage();
		SignupPage signupPage = new SignupPage(driver);
		TestHelper.signup(driver, port);
		Thread.sleep(1000);
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getSuccessMessage());
		Thread.sleep(1000);
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		getHomePage();
		Assertions.assertEquals("Home", driver.getTitle());
	}
}
