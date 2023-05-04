package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.CreateLinkTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/plaid")
public class PlaidController {

    @Autowired
    private CreateLinkTokenService createLinkTokenService;

    private UserRepository userDao;

    public PlaidController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/create-link-token")
    public String createLinkToken() throws IOException {
        System.out.println("Inside createLinkToken");
        User user = userDao.findById(1L).get();
        return createLinkTokenService.getLinkToken(user);
    }

}
