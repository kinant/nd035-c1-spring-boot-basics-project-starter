package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.auth.IAuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CredentialsTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private HomePage homePage;
	private CredentialsTab credentialsTab;

	private static final String TEST_CREDENTIAL_URL = "www.google.com";
	private static final String TEST_CREDENTIAL_USERNAME = "user1";
	private static final String TEST_CREDENTIAL_PASSWORD = "password123";

	private static final String TEST_UPDATED_CREDENTIAL_URL = "www.googly.com";
	private static final String TEST_UPDATED_CREDENTIAL_USERNAME = "user2";
	private static final String TEST_UPDATED_CREDENTIAL_PASSWORD = "newpassword123";

	@Autowired
	private CredentialService credentialService;

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

	public void addNewCredential() throws InterruptedException {
		credentialsTab.addCredential();
		Thread.sleep(1000);
		credentialsTab.inputNewCredential(TEST_CREDENTIAL_URL, TEST_CREDENTIAL_USERNAME, TEST_CREDENTIAL_PASSWORD);
		Thread.sleep(1000);
		credentialsTab.submit();
	}

	@Test
	@Order(1)
	public void getCredentialTabTest() throws InterruptedException {
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
	@Order(2)
	public void addCredentialTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		addNewCredential();
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		// int userId = authenticationFacade.getAuthenticatedUserId();
		Assertions.assertTrue(credentialsTab.checkSecureCredentialExists(TEST_CREDENTIAL_URL, TEST_CREDENTIAL_USERNAME, TEST_CREDENTIAL_PASSWORD, credentialService));
		Thread.sleep(1000);
	}

	@Test
	@Order(3)
	public void viewDecryptedCredentialTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);
		Assertions.assertTrue(
				credentialsTab.checkDecryptedCredentialsExist(
						TEST_CREDENTIAL_URL,
						TEST_CREDENTIAL_USERNAME,
						TEST_CREDENTIAL_PASSWORD
				)
		);
		Thread.sleep(1000);
	}

	@Test
	@Order(4)
	public void updateCredentialsTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);
		credentialsTab.inputNewCredential(
				TEST_UPDATED_CREDENTIAL_URL,
				TEST_UPDATED_CREDENTIAL_USERNAME,
				TEST_UPDATED_CREDENTIAL_PASSWORD
		);
		Thread.sleep(1000);
		credentialsTab.submit();
		Thread.sleep(1000);

		homePage.goToCredsTab();
		Thread.sleep(1000);
		Assertions.assertTrue(
				credentialsTab.checkSecureCredentialExists(
						TEST_UPDATED_CREDENTIAL_URL,
						TEST_UPDATED_CREDENTIAL_USERNAME,
						TEST_UPDATED_CREDENTIAL_PASSWORD,
						credentialService
				)
		);
		Thread.sleep(1000);
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);
		Assertions.assertTrue(
				credentialsTab.checkDecryptedCredentialsExist(
						TEST_UPDATED_CREDENTIAL_URL,
						TEST_UPDATED_CREDENTIAL_USERNAME,
						TEST_UPDATED_CREDENTIAL_PASSWORD
				)
		);
		Thread.sleep(1000);
	}

	@Test
	@Order(5)
	public void deleteCredentialTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 1);
		Thread.sleep(1000);
		credentialsTab.clickDeleteCredential();
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 0);
	}
}