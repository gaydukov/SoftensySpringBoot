package com.softensy.softensyspringboot.service.serviceimpl;

import com.softensy.softensyspringboot.dto.AppointmentDto;
import com.softensy.softensyspringboot.dto.DoctorAppointmentDto;
import com.softensy.softensyspringboot.dto.PatientAppointmentDto;
import com.softensy.softensyspringboot.entity.AbsenceSchedule;
import com.softensy.softensyspringboot.entity.Appointment;
import com.softensy.softensyspringboot.entity.Doctor;
import com.softensy.softensyspringboot.entity.Patient;
import com.softensy.softensyspringboot.exception.DoctorAbsentException;
import com.softensy.softensyspringboot.exception.NotFoundException;
import com.softensy.softensyspringboot.mapper.AbsenceScheduleMapper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.softensy.softensyspringboot.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceImplTest {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @MockBean
    private AbsenceScheduleServiceImpl absenceScheduleService;
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
    @MockBean
    private AbsenceScheduleMapper absenceScheduleMapper;

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
        when(absenceScheduleService.findAllAbsenceScheduleByDoctorId(anyLong())).thenReturn(getAbsenceScheduleListDto());
        when(absenceScheduleMapper.dtoToEntity(getFirstAbsenceScheduleDto())).thenReturn(getFirstAbsenceSchedule());
        AppointmentDto expectedAppointmentDto = appointmentService.createAppointment(actualAppointmentDto);
        //then
        Assertions.assertEquals(expectedAppointmentDto, actualAppointmentDto);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(doctorRepository, times(1)).findById(anyLong());
        verify(patientRepository, times(1)).findById(anyLong());
        verify(absenceScheduleService, times(1)).findAllAbsenceScheduleByDoctorId(anyLong());
        verify(absenceScheduleService, times(1)).isDoctorAbsent(any(AbsenceSchedule.class), any(LocalDateTime.class));
        verify(appointmentRepository).save(any(Appointment.class));
        Assertions.assertNotNull(doctor);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(expectedAppointmentDto);
    }

    @Test
    @DisplayName("checking create new invalid appointment, exception expected")
    void testCreateNewInvalidAppointmentReturnException() {
        assertThrows(NotFoundException.class, () -> appointmentService.createAppointment(null));

    }

    @Test
    @DisplayName("checking create new appointment with invalid date, exception expected")
    void testCreateNewAppointmentWithInvalidDateReturnException() {
        //given
        AppointmentDto appointmentDto = getAppointmentDto(getFirstAppointment());
        // when
        when(absenceScheduleService.findAllAbsenceScheduleByDoctorId(anyLong())).thenReturn(getAbsenceScheduleListDto());
        when(absenceScheduleService.isDoctorAbsent(any(AbsenceSchedule.class), any(LocalDateTime.class))).thenReturn(true);
        when(absenceScheduleMapper.dtoToEntity(getFirstAbsenceScheduleDto())).thenReturn(getFirstAbsenceSchedule());
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(getFirstDoctor()));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(getFirstPatient()));
        //then
        assertThrows(DoctorAbsentException.class, () -> appointmentService.createAppointment(appointmentDto));
        verify(absenceScheduleService, times(1)).isDoctorAbsent(any(AbsenceSchedule.class), any(LocalDateTime.class));

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
        verify(doctorAppointmentMapper, times(1)).entityToDto(appointmentList);
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
        verify(patientAppointmentMapper, times(1)).entityToDto(appointmentList);
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

    @Test
    @DisplayName("checking delete appointment by invalid id, exception expected")
    void testDeleteAppointmentByInvalidId() {
        // when
        when(appointmentRepository.existsById(1L)).thenReturn(false);
        //then
        assertThrows(NotFoundException.class, () -> appointmentService.deleteAppointment(1L));
        verify(appointmentRepository, times(1)).existsById(1L);
    }

}
