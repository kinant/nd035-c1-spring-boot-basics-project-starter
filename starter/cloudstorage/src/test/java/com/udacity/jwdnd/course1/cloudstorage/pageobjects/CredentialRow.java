package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

public class CredentialRow {
    private String url;
    private String username;
    private String password;

    public CredentialRow(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
