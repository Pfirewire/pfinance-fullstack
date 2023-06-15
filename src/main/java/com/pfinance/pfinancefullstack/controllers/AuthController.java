package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.dtos.LoginDto;
import com.pfinance.pfinancefullstack.services.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    private final LoginUserService loginUserService;

    public AuthController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    // Runs LoginUserServer method to authenticate user and return JWT
    @PostMapping("/login")
    public String loginAndReturnToken(@RequestBody LoginDto loginDto, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("Inside loginAndReturnToken");
        return loginUserService.logUserInAndReturnJwtToken(loginDto, req, res);
    }

}
