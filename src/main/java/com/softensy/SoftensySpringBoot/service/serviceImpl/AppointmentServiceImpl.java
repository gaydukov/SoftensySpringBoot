package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment not found");
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<PatientDto> getAllAppointmentsToDoctor(long doctorId) {
        List<Appointment> listAppointments = appointmentRepository.findAll();
        List<PatientDto> result = new ArrayList<>();
        for (Appointment appointment : listAppointments) {
            if (appointment.getDoctor().getId() == doctorId) {
                PatientDto patientDto = PatientDto.builder()
                        .firstName(appointment.getPatient().getFirstName())
                        .lastName(appointment.getPatient().getLastName())
                        .middleName(appointment.getPatient().getMiddleName())
                        .dateOfAppointment(appointment.getDateOfAppointment())
                        .build();
                result.add(patientDto);
            }
        }
        return result;
    }

    @Override
    public List<DoctorDto> getAllPatientAppointments(long patientId) {
        List<Appointment> listAppointments = appointmentRepository.findAll();
        List<DoctorDto> result = new ArrayList<>();
        for (Appointment appointment : listAppointments) {
            if (appointment.getPatient().getId() == patientId) {
                DoctorDto doctorDto = DoctorDto.builder()
                        .firstName(appointment.getDoctor().getFirstName())
                        .lastName(appointment.getDoctor().getLastName())
                        .middleName(appointment.getDoctor().getMiddleName())
                        .position(appointment.getDoctor().getPosition())
                        .dateOfAppointment(appointment.getDateOfAppointment())
                        .build();
                result.add(doctorDto);
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
