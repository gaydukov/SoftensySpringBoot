package com.softensy.SoftensySpringBoot.mapper;

import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityMapper {
    public UserDetails userSecurityToUserDetails(UserSecurity userSecurity) {
        return new User(userSecurity.getLogin(), userSecurity.getPassword(),
                true, true, true, true,
                userSecurity.getRole().getAuthorities());
    }

}
