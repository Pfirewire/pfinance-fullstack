package com.pfinance.pfinancefullstack.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plaid.client.ApiClient;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PlaidClientService {

    @Autowired
    PlaidTokenService plaidToken;

    public PlaidApi createPlaidClient() throws JsonProcessingException {
        System.out.println("Inside createPlaidClient");
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", plaidToken.getPlaidClientId());
        apiKeys.put("secret", plaidToken.getPlaidDevelopmentSecret());
        apiKeys.put("plaidVersion", "2020-09-14");
        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(ApiClient.Development);

//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(plaidToken));

        return apiClient.createService(PlaidApi.class);
    }
}
