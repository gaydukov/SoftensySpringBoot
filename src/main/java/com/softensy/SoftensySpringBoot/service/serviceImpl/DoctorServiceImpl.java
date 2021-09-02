package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepositoty;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepositoty doctorRepositoty;
    @Autowired
    public DoctorServiceImpl(DoctorRepositoty doctorRepositoty) {
        this.doctorRepositoty = doctorRepositoty;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepositoty.findAll();
        if (doctors.isEmpty()) {
            return (List<Doctor>) new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepositoty.findById(id);
        if (doctor.isEmpty()) {
            return doctor;
        }
        return doctor;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else
        doctorRepositoty.save(doctor);
        return doctor;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor == null) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            doctorRepositoty.saveAndFlush(doctor);
        return doctor;
    }

    @Override
    public void deleteDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepositoty.findById(id);
        if (doctor.isEmpty()) {
            ResponseEntity rs = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            doctorRepositoty.deleteById(id);
    }
}
