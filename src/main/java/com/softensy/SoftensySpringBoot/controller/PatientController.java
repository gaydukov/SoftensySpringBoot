package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(patientService.getPatientById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.savePatient(patient), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.updatePatient(patient), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }
}
