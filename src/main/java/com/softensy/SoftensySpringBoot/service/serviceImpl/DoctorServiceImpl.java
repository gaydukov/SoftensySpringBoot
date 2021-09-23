package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.exception.BadRequestException;
import com.softensy.SoftensySpringBoot.exception.NotFoundException;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new NotFoundException("Doctor not found");
        }
        return doctor;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new BadRequestException("Doctor is empty");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new BadRequestException("Doctor is empty");
        }
        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new BadRequestException("Doctor is empty");
        }
        doctorRepository.deleteById(id);
    }

}
