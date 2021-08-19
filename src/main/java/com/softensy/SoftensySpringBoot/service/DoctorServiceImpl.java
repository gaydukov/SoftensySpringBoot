package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entety.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepositoty doctorRepositoty;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepositoty.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepositoty.getById(id);
    }

    @Override
    public void saveDoctor(Doctor doctor) {
        doctorRepositoty.saveAndFlush(doctor);

    }

    @Override
    public void updateDoctor(Doctor doctor) {
        doctorRepositoty.saveAndFlush(doctor);

    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepositoty.deleteById(id);


    }
}
