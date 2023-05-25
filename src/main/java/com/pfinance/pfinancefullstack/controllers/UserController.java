package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.models.UserStatus;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user, HttpServletRequest httpServletRequest) {
        String plainPassword = user.getPassword();
        // Hashing password
        String hash = passwordEncoder.encode(user.getPassword());
        // Setting user password to the hash and saving user to table
        user.setPassword(hash);
        userDao.save(user);
        if(authWithHttpServletRequest(httpServletRequest, user.getUsername(), plainPassword)) {
            return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User registration unsuccessful.", HttpStatus.BAD_REQUEST);
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

    private boolean authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
            return true;
        } catch (ServletException e) {
            e.printStackTrace();
            return false;
        }
    }
}
