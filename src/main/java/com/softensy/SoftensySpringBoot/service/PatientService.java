package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entety.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    public List<Patient> getAllPatients();
    public Optional<Patient> getPatientById(Long id);
    public void savePatient(Patient patient);
    public void updatePatient(Patient patient);
    public void deletePatient(Long id);
}
