package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.NotesTab;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotesTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private HomePage homePage;
	private NotesTab notesTab;

	private static final String TEST_TITLE = "Title 1";
	private static final String TEST_DESCRIPTION = "This is description for title 1";
	private static final String TEST_UPDATED_TITLE = "Updated Title 2";
	private static final String TEST_UPDATED_DESCRIPTION = "This is description for updated note";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.homePage = new HomePage(driver);
		this.notesTab = new NotesTab(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void addNewNote() throws InterruptedException {
		notesTab.addNote();
		Thread.sleep(1000);
		notesTab.inputNewNote(TEST_TITLE, TEST_DESCRIPTION);
		Thread.sleep(1000);
		notesTab.submit();
	}

	@Test
	@Order(1)
	public void getNotesTabTest() throws InterruptedException {
		TestHelper.signup(driver, port);
		Thread.sleep(1000);
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		String addNoteBtnText = driver.findElement(By.id("add-note-btn")).getText();
		Assertions.assertEquals("+ Add a New Note", addNoteBtnText);
	}

	@Test
	@Order(2)
	public void addNoteTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		addNewNote();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals(true, notesTab.checkNoteExists(TEST_TITLE, TEST_DESCRIPTION));
		Thread.sleep(1000);
	}

	@Test
	@Order(3)
	public void updateNoteTest() throws InterruptedException {
		TestHelper.login(driver, port);
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		int numNotes = notesTab.getNumNotes();
		Assertions.assertTrue(numNotes >= 1);
		Thread.sleep(1000);
		notesTab.clickEditNote();
		Thread.sleep(1000);
		notesTab.inputNewNote(TEST_UPDATED_TITLE, TEST_UPDATED_DESCRIPTION);
		Thread.sleep(1000);
		notesTab.submit();
		Thread.sleep(1000);
		homePage.goToNotesTab();
		Thread.sleep(1000);
		// check num notes remains the same
		Assertions.assertEquals(numNotes, notesTab.getNumNotes());
		// check old note doesnt exist
		Assertions.assertFalse(notesTab.checkNoteExists(TEST_TITLE, TEST_DESCRIPTION));
		// check note was updated
		Assertions.assertTrue(notesTab.checkNoteExists(TEST_UPDATED_TITLE, TEST_UPDATED_DESCRIPTION));
		Thread.sleep(1000);
	}

	@Test
	@Order(4)
	public void deleteNoteTest() throws InterruptedException {
		TestHelper.login(driver, port);
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
