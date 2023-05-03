package com.pfinance.pfinancefullstack.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaidTokenService {

    @Value("${plaid.client.id}")
    private String plaidClientId;

    @Value("${plaid.development.secret}")
    private String plaidDevelopmentSecret;

    public PlaidTokenService() {
    }

    public String getPlaidClientId() {
        return plaidClientId;
    }

    public void setPlaidClientId(String plaidClientId) {
        this.plaidClientId = plaidClientId;
    }

    public String getPlaidDevelopmentSecret() {
        return plaidDevelopmentSecret;
    }

    public void setPlaidDevelopmentSecret(String plaidDevelopmentSecret) {
        this.plaidDevelopmentSecret = plaidDevelopmentSecret;
    }
}
