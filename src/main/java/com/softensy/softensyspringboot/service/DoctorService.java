package com.softensy.softensyspringboot.service;

import com.softensy.softensyspringboot.entity.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    Doctor saveDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor);

    void deleteDoctor(Long id);

}
