package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.services.JwtTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                            UNKNOWN IF IN USE
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


@RestController
public class HomeController {

    private final JwtTokenService jwtTokenService;

    public HomeController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

//    @GetMapping("/")
//    public String home(Authentication authentication) {
//        System.out.printf("Token requested for: %s%n", ((User) authentication.getPrincipal()).getUsername());
//        String token = jwtTokenService.generateToken(authentication);
//        System.out.printf("Token granted: %s%n", token);
//        return token;
//    }
}
