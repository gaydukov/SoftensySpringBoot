package com.softensy.softensyspringboot.service.serviceImpl;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.dto.DoctorAppointmentDto;
import com.softensy.softensyspringboot.dto.PatientAppointmentDto;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.mapper.DoctorAppointmentMapper;
import com.softensy.softensyspringboot.mapper.PatientAppointmentMapper;
import com.softensy.softensyspringboot.repository.AppointmentRepository;
import com.softensy.softensyspringboot.repository.DoctorRepository;
import com.softensy.softensyspringboot.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
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
        Doctor doctor = getFirstDoctor();
        Patient patient = getFirstPatient();
        Appointment appointment = getFirstAppointment();
        AppointmentDto actualAppointmentDto = getAppointmentDto(appointment);
        // when
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        AppointmentDto expectedAppointmentDto = appointmentService.createAppointment(actualAppointmentDto);
        //then
        Assertions.assertEquals(expectedAppointmentDto, actualAppointmentDto);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(doctorRepository, times(2)).findById(anyLong());
        verify(patientRepository, times(2)).findById(anyLong());
        verify(appointmentRepository).save(any(Appointment.class));
        Assertions.assertNotNull(doctor);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(expectedAppointmentDto);
    }

    @Test
    @DisplayName("checking all appointments to doctor by doctor Id")
    void testGetAllAppointmentsToDoctorByDoctorIdReturnListPatientDtoWhoAppointmentThisDoctor() {
        // given
        List<Appointment> appointmentList = getAppointmentList();
        List<DoctorAppointmentDto> actualPatientList = getDoctorAppointmentDtoList();
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
        List<Appointment> appointmentList = getAppointmentList();
        List<PatientAppointmentDto> actualDoctorList = getPatientAppointmentDtoList();
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
        Appointment appointment = getFirstAppointment();
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
