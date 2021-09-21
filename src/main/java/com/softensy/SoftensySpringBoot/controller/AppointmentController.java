package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
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
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<DoctorAppointmentDto>> getAllDoctorAppointments(@PathVariable("id") long doctorId) {
        return new ResponseEntity<>(appointmentService.getAllDoctorAppointments(doctorId), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<PatientAppointmentDto>> getAllPatientAppointments(@PathVariable("id") long patientId) {
        return new ResponseEntity<>(appointmentService.getAllPatientAppointments(patientId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
