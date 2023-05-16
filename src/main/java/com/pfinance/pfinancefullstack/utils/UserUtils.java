package com.pfinance.pfinancefullstack.utils;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    private static String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static User currentUser(UserRepository userDao) {
        return userDao.findByUsername(currentUsername());
    }
}
