package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<Map<LocalDateTime, PatientDto>> getAllAppointmentsToDoctor(@PathVariable("id") long doctorId) {
        return new ResponseEntity<>(appointmentService.getAllAppointmentsToDoctor(doctorId), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Map<LocalDateTime, DoctorDto>> getAllPatientAppointments(@PathVariable("id") long patientId) {
        return new ResponseEntity<>(appointmentService.getAllPatientAppointments(patientId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
