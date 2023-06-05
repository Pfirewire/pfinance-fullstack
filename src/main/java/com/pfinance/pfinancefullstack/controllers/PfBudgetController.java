package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfBudget;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfBudgetRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<PfBudget> getPfBudgetsByUser() {
        User user = UserUtils.currentUser(userDao);
        return pfBudgetDao.findAllByUser(user);
    }

    @GetMapping("/budget/current")
    public PfBudget getCurrentPfBudgetByUser() {
        System.out.println("Inside getCurrentPfBudgetByUser");
        User user = UserUtils.currentUser(userDao);
        LocalDate localDate = LocalDate.now();
        return pfBudgetDao.findByUserAndMonthAndYear(user, localDate.getMonth().getValue(), localDate.getYear());
    }

//    @PostMapping("/budgets")
//    public PfBudget createPfBudgetByUser() {
//        User user = UserUtils.currentUser(userDao);
//
//    }


}
