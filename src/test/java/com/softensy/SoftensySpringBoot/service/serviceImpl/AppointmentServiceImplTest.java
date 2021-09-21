package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.*;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
import com.softensy.SoftensySpringBoot.mapper.DoctorAppointmentMapper;
import com.softensy.SoftensySpringBoot.mapper.PatientAppointmentMapper;
import com.softensy.SoftensySpringBoot.repository.AppointmentRepository;
import com.softensy.SoftensySpringBoot.repository.DoctorRepository;
import com.softensy.SoftensySpringBoot.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceImplTest {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @MockBean
    private PatientAppointmentMapper patientAppointmentMapper;
    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private DoctorRepository doctorRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DoctorAppointmentMapper doctorAppointmentMapper;

    @Test
    @DisplayName("checking create new appointment and saves it")
    void testCreateNewAppointmentReturnAppointmentDtoAndVerifyAppointmentSave() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime appointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, appointmentDate);
        AppointmentDto actualAppointmentDto = new AppointmentDto(2, 1, appointmentDate);
        // when
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        AppointmentDto expectedAppointmentDto = appointmentService.createAppointment(actualAppointmentDto);
        //then
        Assertions.assertEquals(expectedAppointmentDto, actualAppointmentDto);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(doctorRepository, times(1)).findById(anyLong());
        verify(patientRepository, times(1)).findById(anyLong());
        verify(appointmentRepository).save(any(Appointment.class));
        Assertions.assertNotNull(doctor);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(expectedAppointmentDto);
    }

    @Test
    @DisplayName("checking all appointments to doctor by doctor Id")
    void testGetAllAppointmentsToDoctorByDoctorIdReturnListPatientDtoWhoAppointmentThisDoctor() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime firstAppointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime secondAppointmentDate = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime thirdAppointmentDate = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, firstAppointmentDate);
        Appointment secondAppointment = new Appointment(2, patient, doctor, secondAppointmentDate);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, thirdAppointmentDate);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        PatientDto firstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto secondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto thirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        DoctorAppointmentDto firstDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(firstPatient)
                .appointmentDate(firstAppointmentDate)
                .build();
        DoctorAppointmentDto secondDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(secondPatient)
                .appointmentDate(secondAppointmentDate)
                .build();
        DoctorAppointmentDto thirdDoctorAppointmentDto = DoctorAppointmentDto.builder()
                .patientDto(thirdPatient)
                .appointmentDate(thirdAppointmentDate)
                .build();
        List<DoctorAppointmentDto> actualPatientList = new ArrayList<>();
        actualPatientList.add(firstDoctorAppointmentDto);
        actualPatientList.add(secondDoctorAppointmentDto);
        actualPatientList.add(thirdDoctorAppointmentDto);
        // when
        when(appointmentRepository.findAllByDoctorId(anyLong())).thenReturn(appointmentList);
        when(doctorAppointmentMapper.entityToDto(appointmentList)).thenReturn(actualPatientList);
        List<DoctorAppointmentDto> expectedPatientList = appointmentService.getAllDoctorAppointments(1L);
        //then
        Assertions.assertEquals(expectedPatientList, actualPatientList);
        Assertions.assertNotNull(expectedPatientList);
        Assertions.assertNotNull(actualPatientList);
        verify(appointmentRepository, times(1)).findAllByDoctorId(anyLong());
        verify(doctorAppointmentMapper, times(1)).entityToDto(any(List.class));
    }

    @Test
    @DisplayName("checking all patient's appointments to doctor by patient Id")
    void testGetAllPatientAppointmentsByPatientIdReturnListDoctorDtoWhichThisPatientIsAssigned() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime firstAppointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime secondAppointmentDate = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime thirdAppointmentDate = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, firstAppointmentDate);
        Appointment secondAppointment = new Appointment(2, patient, doctor, secondAppointmentDate);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, thirdAppointmentDate);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        DoctorDto firstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto secondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto thirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        PatientAppointmentDto firstPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(firstDoctor)
                .appointmentDate(firstAppointmentDate)
                .build();
        PatientAppointmentDto secondPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(secondDoctor)
                .appointmentDate(secondAppointmentDate)
                .build();
        PatientAppointmentDto thirdPatientAppointmentDto = PatientAppointmentDto.builder()
                .doctorDto(thirdDoctor)
                .appointmentDate(thirdAppointmentDate)
                .build();
        List<PatientAppointmentDto> actualDoctorList = new ArrayList<>();
        actualDoctorList.add(firstPatientAppointmentDto);
        actualDoctorList.add(secondPatientAppointmentDto);
        actualDoctorList.add(thirdPatientAppointmentDto);
        // when
        when(appointmentRepository.findAllByPatientId(anyLong())).thenReturn(appointmentList);
        when(patientAppointmentMapper.entityToDto(appointmentList)).thenReturn(actualDoctorList);
        List<PatientAppointmentDto> expectedDoctorList = appointmentService.getAllPatientAppointments(2L);
        //then
        Assertions.assertEquals(expectedDoctorList, actualDoctorList);
        Assertions.assertNotNull(expectedDoctorList);
        Assertions.assertNotNull(actualDoctorList);
        verify(appointmentRepository, times(1)).findAllByPatientId(anyLong());
        verify(patientAppointmentMapper, times(1)).entityToDto(any(List.class));
    }

    @Test
    @DisplayName("checking get by id appointment and delete him")
    void testDeleteAppointmentByIdThenVerifyAppointmentDelete() {
        // given
        Doctor doctor = Doctor.builder()
                .id(1)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .position("Hirurg")
                .dateOfBirth(LocalDate.of(1987, 5, 12))
                .phoneNumber("45632147")
                .build();
        Patient patient = Patient.builder()
                .id(2)
                .firstName("Petr")
                .lastName("Petrov")
                .middleName("Petrov")
                .doctorId(1)
                .dateOfBirth(LocalDate.of(1988, 7, 19))
                .phoneNumber("54321")
                .build();
        LocalDateTime appointmentDate = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, appointmentDate);
        // when
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.existsById(1L)).thenReturn(true);
        appointmentRepository.deleteById(1L);
        appointmentService.deleteAppointment(1L);
        //then
        Assertions.assertNotNull(appointment);
        verify(appointmentRepository, times(2)).deleteById(appointment.getId());
    }

}
