package com.softensy.softensyspringboot.service;

import com.softensy.softensyspringboot.entity.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    Patient savePatient(Patient patient);

    Patient updatePatient(Patient patient);

    void deletePatient(Long id);

}
