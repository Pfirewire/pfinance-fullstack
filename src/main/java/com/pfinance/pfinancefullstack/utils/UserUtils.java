package com.pfinance.pfinancefullstack.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
