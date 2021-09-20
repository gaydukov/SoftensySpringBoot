package com.softensy.SoftensySpringBoot.service.serviceImpl;

import com.softensy.SoftensySpringBoot.dto.AppointmentDto;
import com.softensy.SoftensySpringBoot.dto.DoctorDto;
import com.softensy.SoftensySpringBoot.dto.PatientDto;
import com.softensy.SoftensySpringBoot.entity.Appointment;
import com.softensy.SoftensySpringBoot.entity.Doctor;
import com.softensy.SoftensySpringBoot.entity.Patient;
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
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceImplTest {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private DoctorRepository doctorRepository;
    @MockBean
    private PatientRepository patientRepository;

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
        LocalDateTime dateOfAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, dateOfAppointment);
        AppointmentDto actualAppointmentDto = new AppointmentDto(2, 1, dateOfAppointment);
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
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, dateOfFirstAppointment);
        Appointment secondAppointment = new Appointment(2, patient, doctor, dateOfSecondAppointment);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, dateOfThirdAppointment);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        PatientDto actualFirstPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto actualSecondPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        PatientDto actualThirdPatient = PatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .build();
        Map<LocalDateTime, PatientDto> actualPatientList = new HashMap<>();
        actualPatientList.put(dateOfFirstAppointment, actualFirstPatient);
        actualPatientList.put(dateOfSecondAppointment, actualSecondPatient);
        actualPatientList.put(dateOfThirdAppointment, actualThirdPatient);
        // when
        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        Map<LocalDateTime, PatientDto> expectedPatientList = appointmentService.getAllAppointmentsToDoctor(1L);
        //then
        Assertions.assertEquals(expectedPatientList, actualPatientList);
        Assertions.assertNotNull(expectedPatientList);
        Assertions.assertNotNull(actualPatientList);
        verify(appointmentRepository, times(1)).findAll();
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
        LocalDateTime dateOfFirstAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        LocalDateTime dateOfSecondAppointment = LocalDateTime.of(2021, 11, 2, 10, 30);
        LocalDateTime dateOfThirdAppointment = LocalDateTime.of(2021, 11, 4, 11, 30);
        Appointment firstAppointment = new Appointment(1, patient, doctor, dateOfFirstAppointment);
        Appointment secondAppointment = new Appointment(2, patient, doctor, dateOfSecondAppointment);
        Appointment thirdAppointment = new Appointment(3, patient, doctor, dateOfThirdAppointment);
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(firstAppointment);
        appointmentList.add(secondAppointment);
        appointmentList.add(thirdAppointment);
        DoctorDto actualFirstDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto actualSecondDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        DoctorDto actualThirdDoctor = DoctorDto.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .middleName(doctor.getMiddleName())
                .position(doctor.getPosition())
                .build();
        Map<LocalDateTime, DoctorDto> actualDoctorList = new HashMap<>();
        actualDoctorList.put(dateOfFirstAppointment, actualFirstDoctor);
        actualDoctorList.put(dateOfSecondAppointment, actualSecondDoctor);
        actualDoctorList.put(dateOfThirdAppointment, actualThirdDoctor);
        // when
        when(appointmentRepository.findAll()).thenReturn(appointmentList);
        Map<LocalDateTime, DoctorDto> expectedDoctorList = appointmentService.getAllPatientAppointments(2L);
        //then
        Assertions.assertEquals(expectedDoctorList, actualDoctorList);
        Assertions.assertNotNull(expectedDoctorList);
        Assertions.assertNotNull(actualDoctorList);
        verify(appointmentRepository, times(1)).findAll();
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
        LocalDateTime dateOfAppointment = LocalDateTime.of(2021, 11, 1, 9, 30);
        Appointment appointment = new Appointment(1, patient, doctor, dateOfAppointment);
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
