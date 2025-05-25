package com.example.autodoctorappointments.service;

import com.example.autodoctorappointments.dto.DoctorScheduleDto;
import com.example.autodoctorappointments.model.Doctor;
import com.example.autodoctorappointments.model.DoctorSchedule;
import com.example.autodoctorappointments.repository.DoctorRepository;
import com.example.autodoctorappointments.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public ScheduleService(DoctorScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public DoctorSchedule createSchedule(Long doctorId, DoctorScheduleDto scheduleDto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDate(scheduleDto.getDate());
        schedule.setTimeSlots(scheduleDto.getTimeSlots());
        return scheduleRepository.save(schedule);
    }

    public List<DoctorSchedule> getScheduleByDoctor(Long doctorId, LocalDate date) {
        return scheduleRepository.findByDoctorIdAndDate(doctorId, date);
    }
}