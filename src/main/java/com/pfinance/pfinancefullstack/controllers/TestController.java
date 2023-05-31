package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.CreateLinkTokenService;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import com.plaid.client.model.AccountBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin("http://localhost:3000")
public class TestController {

    @Autowired
    private CreateLinkTokenService createLinkToken;

    private final UserRepository userDao;

    public TestController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/get-user")
    public String getLoggedInUserTest() {
        System.out.println("Inside getLoggedInUser");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }else{
            return "no user";
        }

    }

    @GetMapping("/new-plaid-link")
    public List<PfAccount> getNewPlaidLinkTest() throws IOException {
        return createLinkToken.newPlaidLink(UserUtils.currentUser(userDao).getPlaidLinks().get(0));
    }
}
