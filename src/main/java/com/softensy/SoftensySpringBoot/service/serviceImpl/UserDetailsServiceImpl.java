package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserSecurityMapper userSecurityMapper;
    private final UserSecurityRepository userSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserSecurity userSecurity = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return userSecurityMapper.userSecurityToUserDetails(userSecurity);
    }

}
