package com.pfinance.pfinancefullstack.models;

public class Balances {

    private double available;
    private double current;
    private String isoCurrencyCode;
    private double limit;
    private String unofficialCurrencyCode;

    // TODO: Change to Date object later!
    private String lastUpdatedDatetime;

    public Balances() {
    }

    public Balances(double available, double current, String isoCurrencyCode, double limit, String unofficialCurrencyCode, String lastUpdatedDatetime) {
        this.available = available;
        this.current = current;
        this.isoCurrencyCode = isoCurrencyCode;
        this.limit = limit;
        this.unofficialCurrencyCode = unofficialCurrencyCode;
        this.lastUpdatedDatetime = lastUpdatedDatetime;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public String getIsoCurrencyCode() {
        return isoCurrencyCode;
    }

    public void setIsoCurrencyCode(String isoCurrencyCode) {
        this.isoCurrencyCode = isoCurrencyCode;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getUnofficialCurrencyCode() {
        return unofficialCurrencyCode;
    }

    public void setUnofficialCurrencyCode(String unofficialCurrencyCode) {
        this.unofficialCurrencyCode = unofficialCurrencyCode;
    }

    public String getLastUpdatedDatetime() {
        return lastUpdatedDatetime;
    }

    public void setLastUpdatedDatetime(String lastUpdatedDatetime) {
        this.lastUpdatedDatetime = lastUpdatedDatetime;
    }
}
