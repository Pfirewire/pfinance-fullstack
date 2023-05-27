package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.Expense;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @GetMapping("/transactions")
    public List<Expense> getUserExpenses() {
        List<Expense> userExpenses = new ArrayList<>();



        return userExpenses;
    }
}
