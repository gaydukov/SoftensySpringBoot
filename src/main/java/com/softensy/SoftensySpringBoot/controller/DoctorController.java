package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(doctorService.getDoctorById(id).get(), HttpStatus.OK);
        } catch (NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found", exception);
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
//        try {
            Doctor result = doctorService.saveDoctor(doctor);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
//        } catch (NullPointerException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is empty", exception);
//        }

    }

    @PutMapping
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) {
        try {
            return new ResponseEntity<>(doctorService.updateDoctor(doctor), HttpStatus.OK);
        } catch (NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is empty", exception);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") Long id) {
        try {
            doctorService.deleteDoctor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is not found", exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
            return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
        } catch (NullPointerException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctors are not found", exception);
        }

    }
}
