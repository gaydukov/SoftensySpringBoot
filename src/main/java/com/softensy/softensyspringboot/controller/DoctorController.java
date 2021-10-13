package com.softensy.softensyspringboot.controller;

import com.softensy.softensyspringboot.dto.DoctorDto;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.mapper.DoctorMapper;
import com.softensy.softensyspringboot.service.DoctorService;
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
    private final DoctorMapper doctorMapper;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('admin:read') or hasAuthority('patient:read')")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.getDoctorById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody DoctorDto doctor) {
        Doctor result = doctorService.saveDoctor(doctorMapper.dtoToEntity(doctor));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('doctor:write') and @doctorSecurityService.hasDoctor(#doctor))")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody DoctorDto doctor) {
        return new ResponseEntity<>(doctorService.updateDoctor(doctorMapper.dtoToEntity(doctor)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('doctor:write') and @doctorSecurityService.hasDoctorId(#id))")
    public void deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('admin:read') or hasAuthority('patient:read')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

}
