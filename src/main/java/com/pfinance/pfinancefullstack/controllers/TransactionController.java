package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.PfTransaction;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.JsonPrint;
import com.pfinance.pfinancefullstack.services.PlaidApiService;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
import com.pfinance.pfinancefullstack.services.Validate;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import com.plaid.client.model.Transaction;
import com.plaid.client.model.TransactionsGetRequest;
import com.plaid.client.model.TransactionsGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private Validate validate;
    @Autowired
    private PlaidApiService plaidApi;

    private final UserRepository userDao;

    public TransactionController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/transactions/{id}")
    public List<PfTransaction> getPfTransactionsByPfAccountIdDefault(@PathVariable long id, @PathVariable int days) throws ParseException, IOException {
        System.out.println("Inside getPfTransactionsByPfAccount");
        PfAccount pfAccount = validate.userOwnsPfAccount(id);
        List<PfTransaction> pfTransactions = plaidApi.updateAndReturnPfTransactionsByPfAccountAndDays(pfAccount, 7);
        return pfTransactions;
    }

    @GetMapping("/transactions/{id}/{days}")
    public List<PfTransaction> getPfTransactionsByPfAccountId(@PathVariable long id, @PathVariable int days) throws ParseException, IOException {
        System.out.println("Inside getPfTransactionsByPfAccount");
        PfAccount pfAccount = validate.userOwnsPfAccount(id);
        List<PfTransaction> pfTransactions = plaidApi.updateAndReturnPfTransactionsByPfAccountAndDays(pfAccount, days);
        return pfTransactions;
    }
}
