package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.dtos.LoginDto;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto, HttpServletRequest req, HttpServletResponse res){
        System.out.println("Inside authenticateUser");
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = securityContextHolderStrategy.createEmptyContext();
        sc.setAuthentication(auth);
        securityContextHolderStrategy.setContext(sc);
        securityContextRepository.saveContext(sc, req, res);


        System.out.println(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/token")
    public String token() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.printf("Token requested for: %s%n", (authentication.getPrincipal().get);
        String token = tokenService.generateToken(authentication);
        System.out.printf("Token granted: %s%n", token);
        return token;
    }

    @PostMapping("/login")
    public String loginAndReturnToken(@RequestBody LoginDto loginDto, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("Inside loginAndReturnToken");
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = securityContextHolderStrategy.createEmptyContext();
        sc.setAuthentication(auth);
        securityContextHolderStrategy.setContext(sc);
        securityContextRepository.saveContext(sc, req, res);


        System.out.println(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        System.out.printf("Token requested for: %s%n", ((User) auth.getPrincipal()).getUsername());
        String token = tokenService.generateToken(auth);
        System.out.printf("Token granted: %s%n", token);
        return token;
    }

}
