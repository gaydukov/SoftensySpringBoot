package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entety.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long id) {
        Optional<Doctor> doctor = this.doctorService.getDoctorById(id);

        if (doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody  Doctor doctor) {

        if (doctor == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) {

        if (doctor == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.doctorService.updateDoctor(doctor);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") Long id) {
        Optional<Doctor> doctor = this.doctorService.getDoctorById(id);

        if (doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.doctorService.deleteDoctor(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = this.doctorService.getAllDoctors();

        if (doctors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}
