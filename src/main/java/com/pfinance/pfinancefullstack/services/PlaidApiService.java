package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.PlaidLink;
import com.pfinance.pfinancefullstack.repositories.PfAccountRepository;
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

    private final PfAccountRepository pfAccountDao;

    public PlaidApiService(PfAccountRepository pfAccountDao) {
        this.pfAccountDao = pfAccountDao;
    }

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

    public PfAccount updateBalanceByPfAccount(PfAccount pfAccount) throws IOException {
        final String accessToken = pfAccount.getPlaidLink().getPlaidAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        List<AccountBase> accounts = response.body().getAccounts();
        for(AccountBase account : accounts) {
            if(
                    account.getAccountId().equals(pfAccount.getPlaidAccountId()) &&
                    account.getBalances().getAvailable() != null &&
                    account.getBalances().getCurrent() != null
            ) {
                pfAccount.setAvailableBalance(account.getBalances().getAvailable());
                pfAccount.setCurrentBalance(account.getBalances().getCurrent());
            }
        }
        pfAccountDao.save(pfAccount);
        return pfAccount;
    }
}
