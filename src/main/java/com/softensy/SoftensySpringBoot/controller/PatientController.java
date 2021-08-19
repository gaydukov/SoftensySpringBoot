package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entety.Patient;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long id) {

        Optional<Patient> patient = this.patientService.getPatientById(id);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patient.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody  Patient patient) {

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.patientService.savePatient(patient);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Patient> updateDPatient(@RequestBody Patient patient) {

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.patientService.updatePatient(patient);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long id) {
        Optional<Patient> patient = this.patientService.getPatientById(id);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.patientService.deletePatient(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = this.patientService.getAllPatients();

        if (patients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
