package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    public List<Doctor> getAllDoctors();
    public Optional<Doctor> getDoctorById(Long id);
    public void saveDoctor(Doctor doctor);
    public void updateDoctor(Doctor doctor);
    public void deleteDoctor(Long id);


}
