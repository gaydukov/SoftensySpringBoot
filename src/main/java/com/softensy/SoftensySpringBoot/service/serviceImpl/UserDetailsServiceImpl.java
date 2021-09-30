package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.*;
import com.softensy.SoftensySpringBoot.mapper.UserSecurityMapper;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppointmentRepository appointmentRepository;
    private final UserSecurityMapper userSecurityMapper;
    private final UserSecurityRepository userSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserSecurity userSecurity = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return userSecurityMapper.userSecurityToUserDetails(userSecurity);
    }

    public boolean hasDoctorId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (user.getAccountType() != AccountType.DOCTOR) {
            return false;
        } else {
            return user.getUserId() == id;
        }
    }

    public boolean hasDoctor(Doctor doctor) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (user.getAccountType() != AccountType.DOCTOR) {
            return false;
        } else {
            return user.getUserId() == doctor.getId();
        }
    }

    public boolean hasPatientId(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (user.getAccountType() != AccountType.PATIENT) {
            return false;
        } else {
            return user.getUserId() == id;
        }
    }

    public boolean hasPatient(Patient patient) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        if (user.getAccountType() != AccountType.PATIENT) {
            return false;
        } else {
            return user.getUserId() == patient.getId();
        }
    }

    public boolean hasAuthorityDoctorInAppointment(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Appointment doesn't exists"));
        if (user.getAccountType() != AccountType.DOCTOR) {
            return false;
        } else {
            return appointment.getDoctor().getId() == user.getUserId();
        }
    }

    public boolean hasAuthorityPatientInAppointment(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Appointment doesn't exists"));
        if (user.getAccountType() != AccountType.PATIENT) {
            return false;
        } else {
            return appointment.getDoctor().getId() == user.getUserId();
        }
    }

}
