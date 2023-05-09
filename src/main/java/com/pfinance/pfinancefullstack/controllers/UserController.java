package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.models.UserStatus;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/status")
    public UserStatus getUserStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        boolean userExists = false;
        boolean tokenExists = false;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userExists = true;
            User user = userDao.findByUsername(authentication.getName());
            if(user.getAccessToken() != null) tokenExists = true;
        }
        return new UserStatus(userExists, tokenExists);
    }
}
