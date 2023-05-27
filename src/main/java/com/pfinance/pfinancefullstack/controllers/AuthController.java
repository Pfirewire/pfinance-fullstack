package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.dtos.LoginDto;
import com.pfinance.pfinancefullstack.services.LoginUserService;
import com.pfinance.pfinancefullstack.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private final LoginUserService loginUserService;
    public AuthController(TokenService tokenService, LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto, HttpServletRequest req, HttpServletResponse res){
//        System.out.println("Inside authenticateUser");
//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
//        Authentication auth = authenticationManager.authenticate(authReq);
//        SecurityContext sc = securityContextHolderStrategy.createEmptyContext();
//        sc.setAuthentication(auth);
//        securityContextHolderStrategy.setContext(sc);
//        securityContextRepository.saveContext(sc, req, res);
//
//
//        System.out.println(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//    }
//
//    @PostMapping("/token")
//    public String token() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        System.out.printf("Token requested for: %s%n", (authentication.getPrincipal().get);
//        String token = tokenService.generateToken(authentication);
//        System.out.printf("Token granted: %s%n", token);
//        return token;
//    }

    @PostMapping("/login")
    public String loginAndReturnToken(@RequestBody LoginDto loginDto, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("Inside loginAndReturnToken");
        return loginUserService.logUserInAndReturnJwtToken(loginDto, req, res);
    }

}
