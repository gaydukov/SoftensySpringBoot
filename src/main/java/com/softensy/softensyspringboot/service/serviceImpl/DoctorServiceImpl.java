package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.exception.BadRequestException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import com.softensy.softensyspringboot.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final String DOCTOR_NOT_FOUND = "Doctor not found";
    private final String DOCTOR_IS_EMPTY="Doctor is empty";

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            throw new NotFoundException(DOCTOR_NOT_FOUND);
        }
        return doctors;
    }

    @Override
    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            throw new NotFoundException(DOCTOR_NOT_FOUND);
        }
        return doctor.get();
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new BadRequestException(DOCTOR_IS_EMPTY);
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new BadRequestException(DOCTOR_IS_EMPTY);
        }
        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new BadRequestException(DOCTOR_IS_EMPTY);
        }
        doctorRepository.deleteById(id);
    }

}
