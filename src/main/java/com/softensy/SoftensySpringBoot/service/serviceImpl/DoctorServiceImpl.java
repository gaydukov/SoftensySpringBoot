package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<Doctor> getAllDoctors() throws NullPointerException {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctors are not found");
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) throws NullPointerException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found");
        }
        return doctor;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) throws NullPointerException {
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is empty");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) throws NullPointerException {
        if (doctor == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is empty");
        }
        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public void deleteDoctor(Long id) throws NullPointerException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor is not found");
        }
        doctorRepository.deleteById(id);
    }
}
