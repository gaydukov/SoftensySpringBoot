package com.softensy.softensyspringboot.service.serviceimpl;


import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.exception.BadRequestException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.PatientRepository;
import com.softensy.softensyspringboot.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private static final String PATIENT_NOT_FOUND = "Patient not found";
    private static final String PATIENT_IS_EMPTY = "Patient is empty";

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patient = patientRepository.findAll();
        if (patient.isEmpty()) {
            throw new NotFoundException(PATIENT_NOT_FOUND);
        }
        return patient;
    }

    @Override
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new NotFoundException(PATIENT_NOT_FOUND);
        }
        return patient.get();
    }

    @Override
    public Patient savePatient(Patient patient) {
        if (patient == null) {
            throw new BadRequestException(PATIENT_IS_EMPTY);
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        if (patient == null) {
            throw new BadRequestException(PATIENT_IS_EMPTY);
        }
        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new BadRequestException(PATIENT_IS_EMPTY);
        } else {
            patientRepository.deleteById(id);
        }
    }

}
