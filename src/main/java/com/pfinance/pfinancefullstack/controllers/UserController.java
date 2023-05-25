package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.dtos.LoginDto;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.models.UserStatus;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final LoginUserService loginUserService;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, LoginUserService loginUserService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.loginUserService = loginUserService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        String plainPassword = user.getPassword();
        // Hashing password
        String hash = passwordEncoder.encode(user.getPassword());
        // Setting user password to the hash and saving user to table
        user.setPassword(hash);
        userDao.save(user);
        LoginDto loginDto = new LoginDto(user.getUsername(), plainPassword);
        return loginUserService.logUserInAndReturnJwtToken(loginDto, req, res);
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
