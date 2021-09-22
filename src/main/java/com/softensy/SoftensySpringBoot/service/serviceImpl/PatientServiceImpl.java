package com.softensy.SoftensySpringBoot.service.serviceImpl;


import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.exception.BadRequestException;
import com.softensy.SoftensySpringBoot.exception.NotFoundException;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import com.softensy.SoftensySpringBoot.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patient = patientRepository.findAll();
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        return patient;
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        }
        return patient;
    }

    @Override
    public Patient savePatient(Patient patient) {
        if (patient == null) {
            throw new BadRequestException("Patient is empty");
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        if (patient == null) {
            throw new BadRequestException("Patient is empty");
        }
        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new BadRequestException("Patient is empty");
        } else {
            patientRepository.deleteById(id);
        }
    }

}
