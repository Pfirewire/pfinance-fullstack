package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin("http://localhost:3000")
public class TestController {

    @GetMapping("/get-user")
    public String getLoggedInUser() {
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
}
