package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.NotesTab;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTests {

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
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.inputNewUser();
		signupPage.submitSignup();
	}

	public void login() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.inputLoginCredentials();
		Thread.sleep(1000);
		loginPage.submit();
	}


	@Test
	public void getNotesTabTest() throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		String addNoteBtnText = driver.findElement(By.id("add-note-btn")).getText();
		Assertions.assertEquals("+ Add a New Note", addNoteBtnText);
	}

	@Test
	public void addNoteTest() throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		NotesTab notesTab = new NotesTab(driver);
		notesTab.addNote();
		Thread.sleep(1000);
		notesTab.inputNewNote("Title 1");
		Thread.sleep(1000);
		notesTab.submit();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals(true, notesTab.checkNoteCreated("Title 1"));
		Thread.sleep(1000);
	}

	@Test
	public void updateNoteTest() throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		NotesTab notesTab = new NotesTab(driver);
		addNoteTest();
		Thread.sleep(1000);
		int numNotes = notesTab.getNumNotes();
		Assertions.assertTrue(numNotes >= 1);
		Thread.sleep(1000);
		notesTab.clickEditNote();
		Thread.sleep(1000);
		notesTab.inputNewNote("Updated Title 1");
		Thread.sleep(1000);
		notesTab.submit();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals(numNotes, notesTab.getNumNotes());
		Thread.sleep(1000);
		Assertions.assertTrue(notesTab.checkNoteCreated("Updated Title 1"));
		Thread.sleep(1000);
	}

	@Test
	public void deleteNoteTest() throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		NotesTab notesTab = new NotesTab(driver);
		addNoteTest();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		int numNotes = notesTab.getNumNotes();
		Assertions.assertTrue(numNotes >= 1);
		notesTab.clickDeleteNote();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals(numNotes - 1, notesTab.getNumNotes());
		Thread.sleep(1000);
	}

}
