package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PfAccountController {

    private final UserRepository userDao;

    public PfAccountController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/accounts")
    public List<PfAccount> getPfAccountsByUser() {
        System.out.println("Inside getPfAccountsByUser");
        User user = UserUtils.currentUser(userDao);
        return user.getPfAccounts();
    }
}
