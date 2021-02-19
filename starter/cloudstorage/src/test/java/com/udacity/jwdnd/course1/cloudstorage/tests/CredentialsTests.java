package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private HomePage homePage;
	private CredentialsTab credentialsTab;

	private static final String TEST_CREDENTIAL_URL = "www.google.com";
	private static final String TEST_CREDENTIAL_USERNAME = "user1";
	private static final String TEST_CREDENTIAL_PASSWORD = "password123";

	private String test_salt;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		this.homePage = new HomePage(driver);
		this.credentialsTab = new CredentialsTab(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getCredsTabTest() throws InterruptedException {
		TestHelper.signup(driver, port);
		Thread.sleep(1000);
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		String addNoteBtnText = driver.findElement(By.id("add-creds-btn")).getText();
		Assertions.assertEquals("+ Add a New Credential", addNoteBtnText);
	}

	@Test
	public void addCredentialTest() throws InterruptedException {
		// Testing
		TestHelper.signup(driver, port);
		Thread.sleep(1000);

		//
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		credentialsTab.addCredential();
		Thread.sleep(1000);
		credentialsTab.inputNewCredential(TEST_CREDENTIAL_URL, TEST_CREDENTIAL_USERNAME, TEST_CREDENTIAL_PASSWORD);
		Thread.sleep(1000);
		credentialsTab.submit();
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);

	}
}
