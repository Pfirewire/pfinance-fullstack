package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.dtos.LoginDto;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final JwtTokenService jwtTokenService;

    public LoginUserService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    public String logUserInAndReturnJwtToken(LoginDto loginDto, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("Inside logUserInAndReturnJwtToken");

        // Converting login DTO username and password and using token to authenticate the user
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        // Creating empty security context and setting successfully authenticated user in the context, then saving context
        SecurityContext sc = securityContextHolderStrategy.createEmptyContext();
        sc.setAuthentication(auth);
        securityContextHolderStrategy.setContext(sc);
        securityContextRepository.saveContext(sc, req, res);


        System.out.println(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        System.out.printf("Token requested for: %s%n", ((User) auth.getPrincipal()).getUsername());

        // Generating JWT token to return to front end
        String token = jwtTokenService.generateToken(auth);
        System.out.printf("Token granted: %s%n", token);
        System.out.println("Current user");
        System.out.println(UserUtils.currentUsername());
        return token;
    }
}
