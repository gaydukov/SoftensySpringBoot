package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('appointment:write')")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{id}")
    @PreAuthorize("hasAuthority('doctor:read') and @doctorSecurityService.hasDoctorId(#doctorId)")
    public ResponseEntity<List<DoctorAppointmentDto>> getAllDoctorAppointments(@PathVariable("id") long doctorId) {
        return new ResponseEntity<>(appointmentService.getAllDoctorAppointments(doctorId), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAuthority('patient:read') and @patientSecurityService.hasPatientId(#patientId)")
    public ResponseEntity<List<PatientAppointmentDto>> getAllPatientAppointments(@PathVariable("id") long patientId) {
        return new ResponseEntity<>(appointmentService.getAllPatientAppointments(patientId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('doctor:write') and @appointmentSecurityService.hasAuthorityDoctorInAppointment(#id))" +
            "or (hasAuthority('patient:write') and @appointmentSecurityService.hasAuthorityPatientInAppointment(#id))")
    public void deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
    }

}
