package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Page object that represents the credentials tab page
 */
public class CredentialsTab {
    // the add credentials button
    @FindBy(id = "add-creds-btn")
    private WebElement addCredBtn;

    // url input in the modal form
    @FindBy(id = "credential-url")
    private WebElement urlInput;

    // username input in the modal form
    @FindBy(id = "credential-username")
    private WebElement usernameInput;

    // password input in the modal form
    @FindBy(id = "credential-password")
    private WebElement passwordInput;

    // the table of credentials
    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    public CredentialsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // clicks the add credentials button
    public void addCredential(){
        addCredBtn.click();
    }

    /**
     * Inputs a new credential into the form
     * @param url           The url
     * @param username      The username
     * @param password      The password
     */
    public void inputNewCredential(String url, String username, String password){
        // clear inputs
        urlInput.clear();
        usernameInput.clear();
        passwordInput.clear();

        // populate inputs
        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    // submits the form
    public void submit(){
        passwordInput.submit();
    }

    /**
     * Used to get all the credential rows from the HTML page
     * @return  The list of credential rows found
     */
    public List<CredentialRow> getCredentialRows(){
        List<CredentialRow> credentialRows = new ArrayList<>();

        // get the credentials table, found by elements named credential in the HTML page
        List<WebElement> credentialsList = credentialTable.findElements(By.name("credential"));

        // iterate over every credential
        for(WebElement credential: credentialsList){

            // extract row information
            WebElement urlElement = credential.findElement(By.name("credential-url"));
            WebElement userElement = credential.findElement(By.name("credential-username"));
            WebElement passElement = credential.findElement(By.name("credential-password"));

            String url = urlElement.getAttribute("innerHTML");
            String username = userElement.getAttribute("innerHTML");
            String password = passElement.getAttribute("innerHTML");

            // create a new credential row
            CredentialRow cRow = new CredentialRow(url, username, password);

            // add it to the list
            credentialRows.add(cRow);
        }

        return credentialRows;
    }

    /**
     * Checks if a credential exists in the HTML page (and checks if password is encrypted)
     * @param url                   URL to check
     * @param username              Username to check
     * @param password              Password to check
     * @param credentialService     Reference to credential service
     * @return                      True if the secure (encrypted password) credential is found in the page
     */
    public boolean checkSecureCredentialExists(String url, String username, String password, CredentialService credentialService){
        boolean secureCredentialExists =  false;

        // get a list of all the credential rows in the HTML page
        List<CredentialRow> credentialRows = getCredentialRows();

        // new encryption service
        EncryptionService encryptionService = new EncryptionService();

        // iterate over each credential row found in the page
        for(CredentialRow cRow: credentialRows){

            // get the credential from DB, by credential username and url
            Credential credential = credentialService.getCredentialByURLAndUserName(url, username);

            // encrypt the password so we can check
            String checkPassword = encryptionService.encryptValue(password, credential.getKey());

            // check if the row equals the information we are checking for
            if(cRow.getUrl().equals(url) && cRow.getUsername().equals(username) && cRow.getPassword().equals(checkPassword)){
                secureCredentialExists = true;
            }
        }

        return secureCredentialExists;
    }

    // clicks the edit credentials button
    public void clickEditCredential(){

        // find buttons in the page
        List<WebElement> buttons = credentialTable.findElements(By.tagName("button"));

        // iterate over all the buttons in the page
        for(WebElement button: buttons){

            // find the edit button
            if(button.getTagName().equals("button") && button.getAttribute("innerHTML").equals("Edit")){
                // click it
                button.click();
                break;
            }
        }
    }

    /**
     * Used to find if a Decrypted Credential is shown in the Modal View
     * @param url           URL to check
     * @param username      Username to check
     * @param password      Password to check (decrypted)
     * @return              True if found, false otherwise
     */
    public boolean checkDecryptedCredentialsExist(String url, String username, String password){
        boolean decryptedCredentialExists = false;

        // https://knowledge.udacity.com/questions/334094
        // get the values in the modal form
        String displayedUrl = urlInput.getAttribute("value");
        String displayedUsername = usernameInput.getAttribute("value");
        String displayedPassword = passwordInput.getAttribute("value");

        // check if they match the values being searched for
        if(displayedUrl.equals(url) && displayedUsername.equals(username) && displayedPassword.equals(password)){
            decryptedCredentialExists = true;
        }

        return decryptedCredentialExists;
    }

    // clicks the delete button for a credential row
    public void clickDeleteCredential(){

        // find all <a> tag elements
        List<WebElement> buttons = credentialTable.findElements(By.tagName("a"));

        // iterate over all the "buttons" found
        for(WebElement button: buttons){

            // check for the Delete button
            if(button.getTagName().equals("a") && button.getAttribute("innerHTML").equals("Delete")){
                // click it
                button.click();
                break;
            }
        }
    }

    // returns the number of credential rows in the table
    public int getNumCredentials(){
        return getCredentialRows().size();
    }
}
