package com.pfinance.pfinancefullstack.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pfinance.pfinancefullstack.models.PlaidLink;
import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class PlaidApiService {

    @Autowired
    private PlaidClientService plaidClient;

    public List<AccountBase> getAccountsByPlaidLink(PlaidLink plaidLink) throws IOException {
        final String accessToken = plaidLink.getPlaidAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);

        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        return response.body().getAccounts();
    }
}
