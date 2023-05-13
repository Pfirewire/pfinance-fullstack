package com.pfinance.pfinancefullstack.models;

public class Balances {

    private double available;
    private double current;
    private String iso_currency_code;
    private double limit;
    private String unofficial_currency_code;

    // TODO: Change to Date object later!
    private String last_updated_datetime;

    public Balances() {
    }

    public Balances(double available, double current, String iso_currency_code, double limit, String unofficial_currency_code, String last_updated_datetime) {
        this.available = available;
        this.current = current;
        this.iso_currency_code = iso_currency_code;
        this.limit = limit;
        this.unofficial_currency_code = unofficial_currency_code;
        this.last_updated_datetime = last_updated_datetime;
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

    public String getIso_currency_code() {
        return iso_currency_code;
    }

    public void setIso_currency_code(String iso_currency_code) {
        this.iso_currency_code = iso_currency_code;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getUnofficial_currency_code() {
        return unofficial_currency_code;
    }

    public void setUnofficial_currency_code(String unofficial_currency_code) {
        this.unofficial_currency_code = unofficial_currency_code;
    }

    public String getLast_updated_datetime() {
        return last_updated_datetime;
    }

    public void setLast_updated_datetime(String last_updated_datetime) {
        this.last_updated_datetime = last_updated_datetime;
    }
}
