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

public class CredentialsTab {
    @FindBy(id = "add-creds-btn")
    WebElement addCredBtn;

    @FindBy(id = "credential-url")
    WebElement urlInput;

    @FindBy(id = "credential-username")
    WebElement usernameInput;

    @FindBy(id = "credential-password")
    WebElement passwordInput;

    @FindBy(id = "credentialTable")
    WebElement credentialTable;

    @FindBy(tagName = "form")
    WebElement credentialForm;

    public CredentialsTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addCredential(){
        addCredBtn.click();
    }

    public void inputNewCredential(String url, String username, String password){
        urlInput.clear();
        usernameInput.clear();
        passwordInput.clear();

        urlInput.sendKeys(url);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void submit(){
        passwordInput.submit();
    }

    public List<CredentialRow> getCredentialRows(){
        List<CredentialRow> credentialRows = new ArrayList<>();
        List<WebElement> credentialsList = credentialTable.findElements(By.name("credential"));

        for(WebElement credential: credentialsList){
            WebElement urlElement = credential.findElement(By.name("credential-url"));
            WebElement userElement = credential.findElement(By.name("credential-username"));
            WebElement passElement = credential.findElement(By.name("credential-password"));

            String url = urlElement.getAttribute("innerHTML");
            String username = userElement.getAttribute("innerHTML");
            String password = passElement.getAttribute("innerHTML");

            System.out.println("=========== PRINTING NOTE =============");
            System.out.println("URL: " + url);
            System.out.println("USERNAME: " + username);
            System.out.println("PASSWORD: " + password);

            CredentialRow cRow = new CredentialRow(url, username, password);
            credentialRows.add(cRow);
        }

        System.out.println("GETTING CREDENTIALS ROWS COMPLETE - (size) = " + credentialRows.size());

        return credentialRows;
    }

    public boolean checkSecureCredentialExists(String url, String username, String password, CredentialService credentialService){
        boolean secureCredentialExists =  false;

        List<CredentialRow> credentialRows = getCredentialRows();
        EncryptionService encryptionService = new EncryptionService();

        for(CredentialRow cRow: credentialRows){
            Credential credential = credentialService.getCredentialByURLAndUserName(url, username);
            String checkPassword = encryptionService.encryptValue(password, credential.getKey());

            if(cRow.getUrl().equals(url) && cRow.getUsername().equals(username) && cRow.getPassword().equals(checkPassword)){
                secureCredentialExists = true;
            }
        }

        return secureCredentialExists;
    }

    public void clickEditCredential(){
        List<WebElement> buttons = credentialTable.findElements(By.tagName("button"));

        for(WebElement button: buttons){
            System.out.println("Note element tag: " + button.getTagName());
            System.out.println("Note element inner html: " + button.getAttribute("innerHTML"));
            if(button.getTagName().equals("button") && button.getAttribute("innerHTML").equals("Edit")){
                button.click();
                break;
            }
        }
    }

    public boolean checkDecryptedCredentialsExist(String url, String username, String password){
        boolean decryptedCredentialExists = false;

        // https://knowledge.udacity.com/questions/334094
        String displayedUrl = urlInput.getAttribute("value");
        String displayedUsername = usernameInput.getAttribute("value");
        String displayedPassword = passwordInput.getAttribute("value");

        if(displayedUrl.equals(url) && displayedUsername.equals(username) && displayedPassword.equals(password)){
            decryptedCredentialExists = true;
        }

        return decryptedCredentialExists;
    }

    public void clickDeleteCredential(){
        List<WebElement> buttons = credentialTable.findElements(By.tagName("a"));

        for(WebElement button: buttons){
            System.out.println("Note element tag: " + button.getTagName());
            System.out.println("Note element inner html: " + button.getAttribute("innerHTML"));
            if(button.getTagName().equals("a") && button.getAttribute("innerHTML").equals("Delete")){
                button.click();
                break;
            }
        }
    }

    public int getNumCredentials(){
        return getCredentialRows().size();
    }

}
