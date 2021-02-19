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

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();
		signup();
		login();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signup() throws InterruptedException {
//		driver.get("http://localhost:" + this.port + "/signup");
//		SignupPage signupPage = new SignupPage(driver);
//		signupPage.inputNewUser();
//		signupPage.submitSignup();
	}

	public void login() throws InterruptedException {
//		driver.get("http://localhost:" + this.port + "/login");
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.inputLoginCredentials();
//		Thread.sleep(1000);
//		loginPage.submit();
	}


	@Test
	public void getCredsTabTest() throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		String addNoteBtnText = driver.findElement(By.id("add-creds-btn")).getText();
		Assertions.assertEquals("+ Add a New Credential", addNoteBtnText);
	}

	@Test
	public void addCredentialTest() throws InterruptedException {
		String testUrl = "www.google.com";
		String testUser = "user1";
		String testPassword = "password123";

		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		CredentialsTab credsTab = new CredentialsTab(driver);
		credsTab.addCredential();
		Thread.sleep(1000);
		credsTab.inputNewCredential(testUrl, testUser, testPassword);
		Thread.sleep(1000);
		credsTab.submit();
		Thread.sleep(1000);
		homePage.goToCredsTab();
		Thread.sleep(1000);
		//Assertions.assertEquals(true, credsTab.checkCredentialUrlAndUserCreated(testUrl, testUser));
		Thread.sleep(1000);
	}

//	@Test
//	public void updateNoteTest() throws InterruptedException {
//		HomePage homePage = new HomePage(driver);
//		NotesTab notesTab = new NotesTab(driver);
//		addCredentialTest();
//		Thread.sleep(1000);
//		int numNotes = notesTab.getNumNotes();
//		Assertions.assertTrue(numNotes >= 1);
//		Thread.sleep(1000);
//		notesTab.clickEditNote();
//		Thread.sleep(1000);
//		notesTab.inputNewNote("Updated Title 1");
//		Thread.sleep(1000);
//		notesTab.submit();
//		Thread.sleep(1000);
//		homePage.goToNotesTab();
//		Thread.sleep(1000);
//		Assertions.assertEquals(numNotes, notesTab.getNumNotes());
//		Thread.sleep(1000);
//		Assertions.assertTrue(notesTab.checkNoteCreated("Updated Title 1"));
//		Thread.sleep(1000);
//	}

//	@Test
//	public void deleteNoteTest() throws InterruptedException {
//		HomePage homePage = new HomePage(driver);
//		NotesTab notesTab = new NotesTab(driver);
//		addNoteTest();
//		Thread.sleep(1000);
//		homePage.goToNotesTab();
//		Thread.sleep(1000);
//		int numNotes = notesTab.getNumNotes();
//		Assertions.assertTrue(numNotes >= 1);
//		notesTab.clickDeleteNote();
//		Thread.sleep(1000);
//		homePage.goToNotesTab();
//		Thread.sleep(1000);
//		Assertions.assertEquals(numNotes - 1, notesTab.getNumNotes());
//		Thread.sleep(1000);
//	}

}
