package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.services.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    private final TokenService tokenService;

    public HomeController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        System.out.printf("Token requested for: %s%n", ((User) authentication.getPrincipal()).getUsername());
        String token = tokenService.generateToken(authentication);
        System.out.printf("Token granted: %s%n", token);
        return token;
    }
}
