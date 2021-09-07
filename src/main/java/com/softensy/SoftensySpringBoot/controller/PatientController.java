package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return new ResponseEntity<>(patientService.getPatientById(id).get(), HttpStatus.OK);
        }catch (NullPointerException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient is not found",exception);
        }

    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        try {
            return new ResponseEntity<>(patientService.savePatient(patient), HttpStatus.CREATED);
        }catch (NullPointerException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Patient is empty",exception);
        }

    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        try {
            return new ResponseEntity<>(patientService.updatePatient(patient), HttpStatus.OK);
        }catch (NullPointerException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Patient is empty",exception);
        }

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NullPointerException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Patient is not found");
        }

    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
        }catch (NullPointerException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Patients are not found",exception);
        }

    }
}
