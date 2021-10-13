package com.softensy.softensyspringboot.controller;

import com.softensy.softensyspringboot.dto.PatientDto;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.mapper.PatientMapper;
import com.softensy.softensyspringboot.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('admin:read') or (hasAuthority('patient:read') and @patientSecurityService.hasPatientId(#id))")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long id) {
        return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Patient> savePatient(@RequestBody PatientDto patient) {
        return new ResponseEntity<>(patientService.savePatient(patientMapper.dtoToEntity(patient)), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('patient:write') and @patientSecurityService.hasPatient(#patient))")
    public ResponseEntity<Patient> updatePatient(@RequestBody PatientDto patient) {
        return new ResponseEntity<>(patientService.updatePatient(patientMapper.dtoToEntity(patient)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('admin:write') or (hasAuthority('patient:write') and @patientSecurityService.hasPatientId(#id))")
    public void deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('doctor:read') or hasAuthority('admin:read')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

}
