package com.pfinance.pfinancefullstack.models;

public class Account {

    private String accountId;
    private Balances balances;
    private String mask;
    private String name;
    private String officialName;
    private String subtype;
    private String type;
    private String verificationStatus;
    private String persistentAccountId;

    public Account() {
    }

    public Account(String accountId, Balances balances, String mask, String name, String officialName, String subtype, String type, String verificationStatus, String persistentAccountId) {
        this.accountId = accountId;
        this.balances = balances;
        this.mask = mask;
        this.name = name;
        this.officialName = officialName;
        this.subtype = subtype;
        this.type = type;
        this.verificationStatus = verificationStatus;
        this.persistentAccountId = persistentAccountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Balances getBalances() {
        return balances;
    }

    public void setBalances(Balances balances) {
        this.balances = balances;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getPersistentAccountId() {
        return persistentAccountId;
    }

    public void setPersistentAccountId(String persistentAccountId) {
        this.persistentAccountId = persistentAccountId;
    }
}
