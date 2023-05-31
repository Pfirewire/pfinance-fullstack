package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.JsonPrint;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
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
    private PlaidClientService plaidClient;

    @Autowired
    private JsonPrint jsonPrint;

    private final UserRepository userDao;

    public TransactionController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/transactions/{id}/{days}")
    public List<Transaction> getPfTransactionsByPfAccount(@PathVariable long id, @PathVariable int days) throws ParseException, IOException {
        System.out.println("Inside getPfTransactionsByPfAccount");
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days);

// Pull transactions for a date range

        TransactionsGetRequest request = new TransactionsGetRequest()
                .accessToken(user.getAccessToken())
                .startDate(startDate)
                .endDate(currentDate);

        Response<TransactionsGetResponse> response = plaidClient.createPlaidClient().transactionsGet(request).execute();

        List<Transaction> transactions = new ArrayList <>();
        System.out.println("transactions:");
        jsonPrint.object(response.body().getTransactions());

        transactions.addAll(response.body().getTransactions());

        return transactions;
    }
}
