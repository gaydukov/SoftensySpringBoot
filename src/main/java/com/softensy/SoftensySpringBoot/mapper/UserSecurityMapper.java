package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityMapper {
    public UserDetails userSecurityToUserDetails(UserSecurity userSecurity) {
        return User.builder()
                .username(userSecurity.getLogin())
                .password(userSecurity.getPassword())
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(userSecurity.getRole().getAuthorities())
                .build();
    }

}
