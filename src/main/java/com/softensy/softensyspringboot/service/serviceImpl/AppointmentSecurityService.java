package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.entity.AccountType;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.UserSecurity;
import com.softensy.softensyspringboot.repository.AppointmentRepository;
import com.softensy.softensyspringboot.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentSecurityService {
    private final UserSecurityRepository userSecurityRepository;
    private final AppointmentRepository appointmentRepository;

    public boolean hasAuthorityDoctorInAppointment(long id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSecurity user = userSecurityRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Appointment doesn't exists"));
        if (AccountType.DOCTOR != user.getAccountType()) {
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
        if (AccountType.PATIENT != user.getAccountType()) {
            return false;
        } else {
            return appointment.getDoctor().getId() == user.getUserId();
        }
    }

}
