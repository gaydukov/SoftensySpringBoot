package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.getDoctorById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        Doctor result = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('doctor:write') and @userDetailsServiceImpl.hasDoctor(#doctor))")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(doctor), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('doctor:write') and @userDetailsServiceImpl.hasDoctorId(#id))")
    public void deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

}
