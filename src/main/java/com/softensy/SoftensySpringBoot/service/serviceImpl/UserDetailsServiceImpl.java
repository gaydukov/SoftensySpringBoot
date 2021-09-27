package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.*;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.DoctorSecurityRepository;
import com.softensy.SoftensySpringBoot.repository.PatientSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final DoctorSecurityRepository doctorSecurityRepository;
    private final PatientSecurityRepository patientSecurityRepository;
    private final UserSecurityMapper userSecurityMapper;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserSecurity userSecurity;
        if (getPatientSecurity(login).isPresent()) {
            userSecurity = getPatientSecurity(login).orElseThrow(() ->
                    new UsernameNotFoundException("User doesn't exists"));
        } else {
            userSecurity = getDoctorSecurity(login).orElseThrow(() ->
                    new UsernameNotFoundException("User doesn't exists"));
        }
        return userSecurityMapper.userSecurityToUserDetails(userSecurity);
    }

    public Optional<DoctorSecurity> getDoctorSecurity(String login) {
        return doctorSecurityRepository.findByLogin(login);
    }

    public Optional<PatientSecurity> getPatientSecurity(String login) {
        return patientSecurityRepository.findByLogin(login);
    }

    public boolean hasDoctorId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DoctorSecurity user = doctorSecurityRepository.findByLogin(login).get();
        return user.getDoctor().getId() == id;
    }

    public boolean hasDoctor(Doctor doctor) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DoctorSecurity user = doctorSecurityRepository.findByLogin(login).get();
        return user.getDoctor().getId() == doctor.getId();
    }

    public boolean hasPatientId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PatientSecurity user = patientSecurityRepository.findByLogin(login).get();
        return user.getPatient().getId() == id;
    }

    public boolean hasPatient(Patient patient) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PatientSecurity user = patientSecurityRepository.findByLogin(login).get();
        return user.getPatient().getId() == patient.getId();
    }

}
