package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfBudget;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfBudgetRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PfBudgetController {

    private final UserRepository userDao;
    private final PfBudgetRepository pfBudgetDao;

    public PfBudgetController(UserRepository userDao, PfBudgetRepository pfBudgetDao) {
        this.userDao = userDao;
        this.pfBudgetDao = pfBudgetDao;
    }

    @GetMapping("/budgets")
    public List<PfBudget> getUserPfBudgets() {
        User user = UserUtils.currentUser(userDao);
        return pfBudgetDao.findAllByUser(user);
    }


}
