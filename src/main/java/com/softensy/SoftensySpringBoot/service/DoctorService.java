package com.softensy.SoftensySpringBoot.service;

import com.softensy.SoftensySpringBoot.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    List<Doctor> getAllDoctors();

    Optional<Doctor> getDoctorById(Long id);

    Doctor saveDoctor(Doctor doctor);

    Doctor updateDoctor(Doctor doctor);

    void deleteDoctor(Long id);

}