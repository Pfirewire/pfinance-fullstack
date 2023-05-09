package com.pfinance.pfinancefullstack.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pfinance.pfinancefullstack.models.User;
import com.plaid.client.ApiClient;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import com.plaid.client.model.LinkTokenCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class CreateLinkTokenService {

    @Autowired
    private PlaidTokenService plaidToken;

    private PlaidApi plaidClient;

    @Autowired
    private PlaidClientService plaidClientService;

    public String getLinkToken(User currentUser) throws IOException {
        System.out.println("Inside getLinkToken");

        plaidClient = plaidClientService.createPlaidClient();

        System.out.println("Plaid Client: ");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(plaidClient));


        String linkToken = "";

        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(currentUser.getId().toString())
//                .legalName(currentUser.getName())
//                .phoneNumber(currentUser.getPhoneNumber())
//                .emailAddress(currentUser.getEmail())
        ;

        DepositoryFilter types = new DepositoryFilter()
                .accountSubtypes(Arrays.asList(DepositoryAccountSubtype.CHECKING));

        LinkTokenAccountFilters accountFilters = new LinkTokenAccountFilters()
                .depository(types);

        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .clientId(plaidToken.getPlaidClientId())
                .secret(plaidToken.getPlaidDevelopmentSecret())
                .user(user)
                .clientName("Pfinance")
                .products(Arrays.asList(Products.AUTH, Products.TRANSACTIONS))
                .countryCodes(Arrays.asList(CountryCode.US))
                .language("en")
//                .redirectUri("https://localhost:3000/budget")
                .linkCustomizationName("default")
//                .accountFilters(accountFilters)
        ;

        System.out.println(request);

        Response<LinkTokenCreateResponse> response = plaidClient
                .linkTokenCreate(request)
                .execute();

        System.out.println(response);

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(response));

        if(response.code() == 400) {
            return "400 Error";
        }
        linkToken = response.body().getLinkToken();

        System.out.println(linkToken);
        return linkToken;
    }

//    @Override
//    public void handle(ServerHttpRequest request, ServerHttpResponse response) {
//        return null;
//    }
}
