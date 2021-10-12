package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.entity.AccountType;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientSecurityService {
    private final UserSecurityRepository userSecurityRepository;

    public boolean hasPatientId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (AccountType.PATIENT != user.getAccountType()) {
            return false;
        } else {
            return user.getUserId() == id;
        }
    }

    public boolean hasPatient(Patient patient) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (AccountType.PATIENT != user.getAccountType()) {
            return false;
        } else {
            return user.getUserId() == patient.getId();
        }
    }

}
