package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfAccountRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.PlaidApiService;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
import com.pfinance.pfinancefullstack.services.Validate;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BalanceController {

    @Autowired
    private PlaidClientService plaidClient;
    @Autowired
    private PlaidApiService plaidApi;
    @Autowired
    private Validate validate;

    private UserRepository userDao;
    private PfAccountRepository pfAccountDao;

    public BalanceController(UserRepository userDao, PfAccountRepository pfAccountDao) {
        this.userDao = userDao;
        this.pfAccountDao = pfAccountDao;
    }

    @GetMapping("/balance/{id}")
    public PfAccount getBalanceByPfAccountId(@PathVariable long id) throws IOException {
        System.out.println("Inside testGetBudget");
        PfAccount pfAccount = validate.userOwnsPfAccount(id);
        pfAccount = plaidApi.updateAndReturnBalanceByPfAccount(pfAccount);
        return pfAccount;
    }
}
