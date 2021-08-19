package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entety.Doctor;

import java.util.List;

public interface DoctorService {

    public List<Doctor> getAllDoctors();
    public Doctor getDoctorById(Long id);
    public void saveDoctor(Doctor doctor);
    public void updateDoctor(Doctor doctor);
    public void deleteDoctor(Long id);


}
