package com.softensy.softensyspringboot.controller;

import com.softensy.softensyspringboot.dto.AbsenceScheduleDto;
import com.softensy.softensyspringboot.service.AbsenceScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class AbsenceScheduleController {
    private final AbsenceScheduleService absenceScheduleService;

    @PostMapping("/sickleave")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<AbsenceScheduleDto> createSickLeave(@RequestBody AbsenceScheduleDto absenceScheduleDto) {
        return new ResponseEntity<>(absenceScheduleService.createSickLeave(absenceScheduleDto), HttpStatus.CREATED);
    }

    @PostMapping("/vocation")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<AbsenceScheduleDto> createVocation(@RequestBody AbsenceScheduleDto absenceScheduleDto) {
        return new ResponseEntity<>(absenceScheduleService.createVocation(absenceScheduleDto), HttpStatus.CREATED);
    }

    @PutMapping("/sickleave")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<AbsenceScheduleDto> closeSickLeave(@RequestBody AbsenceScheduleDto absenceScheduleDto) {
        return new ResponseEntity<>(absenceScheduleService.closeSickLeave(absenceScheduleDto), HttpStatus.OK);
    }

    @GetMapping("{doctorId}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<AbsenceScheduleDto>> getAbsenceScheduleList(@PathVariable Long doctorId) {
        return new ResponseEntity<>(absenceScheduleService.findAllAbsenceScheduleByDoctorId(doctorId), HttpStatus.OK);
    }

}
