package com.softensy.SoftensySpringBoot.service.serviceImpl;


import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import com.softensy.SoftensySpringBoot.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patient = patientRepository.findAll();
        if (patient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patients not found");
        }
        return patient;
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        return patient;
    }

    @Override
    public Patient savePatient(Patient patient) {
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient is empty");
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient is empty");
        }
        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found");
        } else {
            patientRepository.deleteById(id);
        }
    }
}
