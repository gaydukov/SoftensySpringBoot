package com.softensy.SoftensySpringBoot.controller;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Visit;
import com.softensy.SoftensySpringBoot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {
    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody Visit visit) {
        return new ResponseEntity<>(visitService.createVisit(visit), HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<PatientDto>> getAllVisitsToDoctor(@PathVariable("id") long doctorId) {
        return new ResponseEntity<>(visitService.getAllVisitsToDoctor(doctorId), HttpStatus.OK);
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<DoctorDto>> getAllPatientVisits(@PathVariable("id") long patientId) {
        return new ResponseEntity<>(visitService.getAllPatientVisits(patientId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Visit> deleteVisit(@PathVariable long id) {
        visitService.deleteVisit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
