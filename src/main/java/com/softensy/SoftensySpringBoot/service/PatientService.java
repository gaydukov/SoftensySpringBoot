package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);

    Patient savePatient(Patient patient);

    Patient updatePatient(Patient patient);

    void deletePatient(Long id);

}
