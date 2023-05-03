package com.pfinance.pfinancefullstack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateLinkTokenService {

    @Autowired
    private PlaidTokenService plaidToken;

    public String getLinkToken() {
        String linkToken = "";

        return linkToken;
    }
}
