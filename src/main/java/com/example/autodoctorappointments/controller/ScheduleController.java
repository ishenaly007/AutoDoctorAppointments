package com.example.autodoctorappointments.controller;

import com.example.autodoctorappointments.dto.DoctorScheduleDto;
import com.example.autodoctorappointments.model.DoctorSchedule;
import com.example.autodoctorappointments.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctors/{doctorId}/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<DoctorSchedule> createSchedule(@PathVariable Long doctorId,
                                                         @Valid @RequestBody DoctorScheduleDto scheduleDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(doctorId, scheduleDto));
    }

    @GetMapping
    public ResponseEntity<List<DoctorSchedule>> getSchedule(@PathVariable Long doctorId,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getScheduleByDoctor(doctorId, date));
    }
}