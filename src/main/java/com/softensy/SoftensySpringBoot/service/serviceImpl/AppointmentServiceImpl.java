package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorAppointmentDto;
import com.softensy.SoftensySpringBoot.dto.PatientAppointmentDto;
import com.softensy.SoftensySpringBoot.exception.NotFoundException;
import com.softensy.SoftensySpringBoot.mapper.AppointmentMapper;
import com.softensy.SoftensySpringBoot.mapper.DoctorAppointmentMapper;
import com.softensy.SoftensySpringBoot.mapper.PatientAppointmentMapper;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorAppointmentMapper doctorAppointmentMapper;
    private final PatientAppointmentMapper patientAppointmentMapper;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        AppointmentMapper appointmentMapper = new AppointmentMapper(doctorRepository, patientRepository);
        if (appointmentDto == null) {
            throw new NotFoundException("Appointment not found");
        }
        appointmentRepository.save(appointmentMapper.dtoToEntity(appointmentDto));
        return appointmentDto;
    }

    @Override
    public List<DoctorAppointmentDto> getAllDoctorAppointments(long doctorId) {
        return doctorAppointmentMapper.entityToDto(appointmentRepository.findAllByDoctorId(doctorId));
    }

    @Override
    public List<PatientAppointmentDto> getAllPatientAppointments(long patientId) {
        return patientAppointmentMapper.entityToDto(appointmentRepository.findAllByPatientId(patientId));
    }

    @Override
    public void deleteAppointment(long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

}
