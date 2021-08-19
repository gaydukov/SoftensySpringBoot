package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entety.Patient;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {


    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepository.saveAndFlush(patient);

    }

    @Override
    public void updatePatient(Patient patient) {
        patientRepository.saveAndFlush(patient);

    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);

    }
}
