package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BalanceController {

    @Autowired
    private PlaidClientService plaidClient;

    private UserRepository userDao;

    public BalanceController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/budget/get")
    public void testGetBudget() throws IOException {
        User user = userDao.findByUsername(UserUtils.currentUsername());
        final String accessToken = user.getAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);

        Response<AccountsGetResponse> response = plaidClient.createPlaidClient()

                .accountsBalanceGet(request)

                .execute();

        List<Account> accounts = response.body().getAccounts();
    }
}
