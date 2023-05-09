package com.pfinance.pfinancefullstack.models;

public class UserStatus {

    private boolean userExists;

    private boolean tokenExists;

    public UserStatus() {
    }

    public UserStatus(boolean userExists, boolean tokenExists) {
        this.userExists = userExists;
        this.tokenExists = tokenExists;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }

    public boolean isTokenExists() {
        return tokenExists;
    }

    public void setTokenExists(boolean tokenExists) {
        this.tokenExists = tokenExists;
    }
}
