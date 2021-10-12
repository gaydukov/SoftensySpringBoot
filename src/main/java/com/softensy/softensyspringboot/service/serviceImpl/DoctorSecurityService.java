package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.entity.AccountType;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorSecurityService {
    private final UserSecurityRepository userSecurityRepository;

    public boolean hasDoctorId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (AccountType.DOCTOR != user.getAccountType()) {
            return false;
        } else {
            return user.getUserId() == id;
        }
    }

    public boolean hasDoctor(Doctor doctor) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (AccountType.DOCTOR != user.getAccountType()) {
            return false;
        } else {
            return user.getUserId() == doctor.getId();
        }
    }

}
