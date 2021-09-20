package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found");
        }
        Appointment appointment = Appointment.builder()
                .doctor(doctorRepository.findById(appointmentDto.getDoctorId()).get())
                .patient(patientRepository.findById(appointmentDto.getPatientId()).get())
                .dateOfAppointment(appointmentDto.getDateOfAppointment())
                .build();
        appointmentRepository.save(appointment);
        return appointmentDto;
    }

    @Override
    public Map<LocalDateTime, PatientDto> getAllAppointmentsToDoctor(long doctorId) {
        List<Appointment> listAppointments = appointmentRepository.findAll();
        Map<LocalDateTime, PatientDto> result = new HashMap<>();
        for (Appointment appointment : listAppointments) {
            if (appointment.getDoctor().getId() == doctorId) {
                PatientDto patientDto = PatientDto.builder()
                        .firstName(appointment.getPatient().getFirstName())
                        .lastName(appointment.getPatient().getLastName())
                        .middleName(appointment.getPatient().getMiddleName())
                        .build();
                result.put(appointment.getDateOfAppointment(), patientDto);
            }
        }
        return result;
    }

    @Override
    public Map<LocalDateTime, DoctorDto> getAllPatientAppointments(long patientId) {
        List<Appointment> listAppointments = appointmentRepository.findAll();
        Map<LocalDateTime, DoctorDto> result = new HashMap<>();
        for (Appointment appointment : listAppointments) {
            if (appointment.getPatient().getId() == patientId) {
                DoctorDto doctorDto = DoctorDto.builder()
                        .firstName(appointment.getDoctor().getFirstName())
                        .lastName(appointment.getDoctor().getLastName())
                        .middleName(appointment.getDoctor().getMiddleName())
                        .position(appointment.getDoctor().getPosition())
                        .build();
                result.put(appointment.getDateOfAppointment(), doctorDto);
            }
        }
        return result;
    }

    @Override
    public void deleteAppointment(long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

}
