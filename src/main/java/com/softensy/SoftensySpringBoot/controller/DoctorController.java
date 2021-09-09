package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(doctorService.getDoctorById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        Doctor result = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(doctor), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

}
