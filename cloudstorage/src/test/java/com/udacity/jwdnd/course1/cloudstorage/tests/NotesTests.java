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

	// test note data
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
		// inits
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

	// used to input and sumbit a new note
	public void addNewNote() throws InterruptedException {
		// click the add note button
		notesTab.addNote();
		Thread.sleep(1000);

		// input data
		notesTab.inputNewNote(TEST_TITLE, TEST_DESCRIPTION);
		Thread.sleep(1000);

		// submit the form
		notesTab.submit();
	}

	/**
	 * Tests that we can access the Note Tab Page
	 * @throws InterruptedException
	 */
	@Test
	@Order(1)
	public void getNotesTabTest() throws InterruptedException {
		// signup
		TestHelper.signup(driver, port);
		Thread.sleep(1000);

		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// check that the add new note button exists (for checking the correct tab pane)
		String addNoteBtnText = driver.findElement(By.id("add-note-btn")).getText();
		Assertions.assertEquals("+ Add a New Note", addNoteBtnText);
	}

	/**
	 * Tests that we can add a note
	 * @throws InterruptedException
	 */
	@Test
	@Order(2)
	public void addNoteTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// add a new note
		addNewNote();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// check that the note was added to the list
		Assertions.assertEquals(true, notesTab.checkNoteExists(TEST_TITLE, TEST_DESCRIPTION));
		Thread.sleep(1000);
	}

	/**
	 * Tests that we can update a note
	 * @throws InterruptedException
	 */
	@Test
	@Order(3)
	public void updateNoteTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// check that a note exists
		Assertions.assertTrue(notesTab.getNumNotes() == 1);
		Thread.sleep(1000);

		// click the edit note button
		notesTab.clickEditNote();
		Thread.sleep(1000);

		// input updated note details
		notesTab.inputNewNote(TEST_UPDATED_TITLE, TEST_UPDATED_DESCRIPTION);
		Thread.sleep(1000);
		notesTab.submit();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);
		// check no new note was added
		Assertions.assertTrue(notesTab.getNumNotes() == 1);
		// check note was updated
		Assertions.assertTrue(notesTab.checkNoteExists(TEST_UPDATED_TITLE, TEST_UPDATED_DESCRIPTION));
	}

	/**
	 * Tests that we can delete a note
	 * @throws InterruptedException
	 */
	@Test
	@Order(4)
	public void deleteNoteTest() throws InterruptedException {
		// log in
		TestHelper.login(driver, port);
		Thread.sleep(1000);

		// go to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// make sure a note exists
		Assertions.assertTrue(notesTab.getNumNotes() == 1);

		// delete the note
		notesTab.clickDeleteNote();
		Thread.sleep(1000);

		// go back to home page
		TestHelper.goHome(driver, port);
		Thread.sleep(1000);

		// go back to the notes tab
		homePage.goToNotesTab();
		Thread.sleep(1000);

		// check that no notes exist
		Assertions.assertTrue(notesTab.getNumNotes() == 0);
	}
}
