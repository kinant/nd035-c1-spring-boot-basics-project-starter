package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePage;
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
		// get the login page
		driver.get("http://localhost:" + this.port + "/login");
	}

	public void getSignupPage() {
		// get the signup page
		driver.get("http://localhost:" + this.port + "/signup");
	}

	public void getHomePage(){
		// get the home page
		driver.get("http://localhost:" + this.port + "/home");
	}

	@Test
	@Order(1)
	public void getLoginPageTest() throws InterruptedException {
		// go to login page
		getLoginPage();
		Thread.sleep(1000);
		// check page title is "Login"
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getSingupPageTest() throws InterruptedException {
		// go to signup page
		getSignupPage();
		Thread.sleep(1000);
		// check page title is "Sign Up"
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void unauthorizedAccessPagesTest() throws InterruptedException {
		// attempt to go to home page
		getHomePage();
		Thread.sleep(1000);
		// assert that the login page is returned instead
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(1000);

		// make sure can still access signup and login pages
		getSignupPage();
		Assertions.assertEquals("Sign Up", driver.getTitle());
		Thread.sleep(1000);
		getLoginPage();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void signupTest() throws InterruptedException {
		// sign up
		TestHelper.signup(driver, port);
		Thread.sleep(1000);

		// get signup page object
		SignupPage signupPage = new SignupPage(driver);

		// check that we were able to signup successfully (by checking for success message)
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signupPage.getSuccessMessage());
		Thread.sleep(1000);
	}

	@Test
	@Order(5)
	public void loginTest() throws InterruptedException {
		// login
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to home page
		getHomePage();
		Thread.sleep(1000);
		// check that we were able to get to home page
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(6)
	public void logoutTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to home page
		getHomePage();
		Thread.sleep(1000);

		// get home page object
		HomePage homePage = new HomePage(driver);

		// log out
		homePage.logout();
		Thread.sleep(1000);

		// check we are returned to log in page
		Assertions.assertEquals("Login", driver.getTitle());

		// attempt to access home page
		getHomePage();
		Thread.sleep(1000);

		// check we are redirected to login page
		Assertions.assertEquals("Login", driver.getTitle());
	}
}
