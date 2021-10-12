package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.dto.DoctorAppointmentDto;
import com.softensy.softensyspringboot.dto.PatientAppointmentDto;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.mapper.AppointmentMapper;
import com.softensy.softensyspringboot.mapper.DoctorAppointmentMapper;
import com.softensy.softensyspringboot.mapper.PatientAppointmentMapper;
import com.softensy.softensyspringboot.repository.AppointmentRepository;
import com.softensy.softensyspringboot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorAppointmentMapper doctorAppointmentMapper;
    private final PatientAppointmentMapper patientAppointmentMapper;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
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
