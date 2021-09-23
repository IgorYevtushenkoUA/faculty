package com.example.faculty.util;

import com.example.faculty.entety.User;
import com.example.faculty.enums.ROLE;
import org.springframework.security.core.context.SecurityContextHolder;

public class Methods {

    public static String getRole() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
            return ROLE.ROLE_GUEST.name();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRole().getName();
    }

}
