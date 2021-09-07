package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            return (List<Doctor>) new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return doctor;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor == null) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            doctorRepository.deleteById(id);
        }
    }
}
