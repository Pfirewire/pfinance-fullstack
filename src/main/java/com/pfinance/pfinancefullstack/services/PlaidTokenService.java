package com.pfinance.pfinancefullstack.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaidTokenService {

    @Value("${plaid.client.id}")
    private String plaidClientId;

    @Value("${plaid.sandbox.secret}")
    private String plaidSandboxSecret;

    @Value("${plaid.development.secret}")
    private String plaidDevelopmentSecret;

    @Value("${plaid.environment}")
    private String plaidEnvironment;

    public PlaidTokenService() {
    }

    public String getPlaidClientId() {
        return plaidClientId;
    }

    public void setPlaidClientId(String plaidClientId) {
        this.plaidClientId = plaidClientId;
    }

    public String getPlaidSandboxSecret() {
        return plaidSandboxSecret;
    }

    public void setPlaidSandboxSecret(String plaidSandboxSecret) {
        this.plaidSandboxSecret = plaidSandboxSecret;
    }

    public String getPlaidDevelopmentSecret() {
        return plaidDevelopmentSecret;
    }

    public void setPlaidDevelopmentSecret(String plaidDevelopmentSecret) {
        this.plaidDevelopmentSecret = plaidDevelopmentSecret;
    }

    public String getPlaidEnvironment() {
        return plaidEnvironment;
    }

    public void setPlaidEnvironment(String plaidEnvironment) {
        this.plaidEnvironment = plaidEnvironment;
    }
}
