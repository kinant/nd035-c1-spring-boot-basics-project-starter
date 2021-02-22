package com.udacity.jwdnd.course1.cloudstorage.tests;

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

/**
 * Test class for Credential Tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CredentialsTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	// reference to homepage and credentials tab page
	private HomePage homePage;
	private CredentialsTab credentialsTab;

	// test data
	private static final String TEST_CREDENTIAL_URL = "www.google.com";
	private static final String TEST_CREDENTIAL_USERNAME = "user1";
	private static final String TEST_CREDENTIAL_PASSWORD = "password123";

	private static final String TEST_UPDATED_CREDENTIAL_URL = "www.googly.com";
	private static final String TEST_UPDATED_CREDENTIAL_USERNAME = "user2";
	private static final String TEST_UPDATED_CREDENTIAL_PASSWORD = "newpassword123";

	// reference to credential service
	@Autowired
	private CredentialService credentialService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		// inits
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

	/**
	 * Inputs and adds a new credential
	 * @throws InterruptedException
	 */
	public void addNewCredential() throws InterruptedException {
		// click the button
		credentialsTab.addCredential();
		Thread.sleep(1000);

		// input data
		credentialsTab.inputNewCredential(TEST_CREDENTIAL_URL, TEST_CREDENTIAL_USERNAME, TEST_CREDENTIAL_PASSWORD);
		Thread.sleep(1000);

		//submit form
		credentialsTab.submit();
	}

	/**
	 * Tests access to credentials tab page
	 * @throws InterruptedException
	 */
	@Test
	@Order(1)
	public void getCredentialTabTest() throws InterruptedException {
		// sign up
		TestHelper.signup(driver, port);
		Thread.sleep(1000);

		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// check that the add new credentials button exists (to test for correct tab)
		String addNoteBtnText = driver.findElement(By.id("add-creds-btn")).getText();
		Assertions.assertEquals("+ Add a New Credential", addNoteBtnText);
	}

	/**
	 * Tests the addition of a credential
	 * and that it's encrypted password is shown after being added
	 * @throws InterruptedException
	 */
	@Test
	@Order(2)
	public void addCredentialTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// add a new credential
		addNewCredential();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// check that the secure encrypted credential shows up in the list
		Assertions.assertTrue(
				credentialsTab.checkSecureCredentialExists(
						TEST_CREDENTIAL_URL,
						TEST_CREDENTIAL_USERNAME,
						TEST_CREDENTIAL_PASSWORD,
						credentialService
				)
		);
	}

	/**
	 * Tests that a decrypted credential is shown in the modal view
	 * @throws InterruptedException
	 */
	@Test
	@Order(3)
	public void viewDecryptedCredentialTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// click the edit button to open up the modal view of credentials
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);

		// check that the decrypted password is shown (should be the password used to create)
		Assertions.assertTrue(
				credentialsTab.checkDecryptedCredentialsExist(
						TEST_CREDENTIAL_URL,
						TEST_CREDENTIAL_USERNAME,
						TEST_CREDENTIAL_PASSWORD
				)
		);
	}

	/**
	 * Tests that a credential is updated
	 * @throws InterruptedException
	 */
	@Test
	@Order(4)
	public void updateCredentialsTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// check that a credential exists
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 1);

		// click on edit credentials
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);

		// input updated credentials
		credentialsTab.inputNewCredential(
				TEST_UPDATED_CREDENTIAL_URL,
				TEST_UPDATED_CREDENTIAL_USERNAME,
				TEST_UPDATED_CREDENTIAL_PASSWORD
		);
		Thread.sleep(1000);
		credentialsTab.submit();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// check no new credential was added
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 1);

		// check that the secure (encrypted) credentials are shown in list
		Assertions.assertTrue(
				credentialsTab.checkSecureCredentialExists(
						TEST_UPDATED_CREDENTIAL_URL,
						TEST_UPDATED_CREDENTIAL_USERNAME,
						TEST_UPDATED_CREDENTIAL_PASSWORD,
						credentialService
				)
		);
		Thread.sleep(1000);

		// click on edit credentials to open up the modal view
		credentialsTab.clickEditCredential();
		Thread.sleep(1000);

		// check that the decrypted updated credentials are shown
		Assertions.assertTrue(
				credentialsTab.checkDecryptedCredentialsExist(
						TEST_UPDATED_CREDENTIAL_URL,
						TEST_UPDATED_CREDENTIAL_USERNAME,
						TEST_UPDATED_CREDENTIAL_PASSWORD
				)
		);
	}

	/**
	 * Tests that a credential is deleted
	 * @throws InterruptedException
	 */
	@Test
	@Order(5)
	public void deleteCredentialTest() throws InterruptedException {
		// login
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);
		// make sure we have one credential in the list
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 1);
		Thread.sleep(1000);

		// click the delete button
		credentialsTab.clickDeleteCredential();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to credentials tab
		homePage.goToCredsTab();
		Thread.sleep(1000);

		// check that we no longer have credentials
		Assertions.assertTrue(credentialsTab.getNumCredentials() == 0);
	}
}