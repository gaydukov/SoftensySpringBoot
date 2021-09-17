package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointment), HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<PatientDto>> getAllAppointmentsToDoctor(@PathVariable("id") long doctorId) {
        return new ResponseEntity<>(appointmentService.getAllAppointmentsToDoctor(doctorId), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorDto>> getAllPatientAppointments(@PathVariable("id") long patientId) {
        return new ResponseEntity<>(appointmentService.getAllPatientAppointments(patientId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
