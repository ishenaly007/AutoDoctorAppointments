package com.example.autodoctorappointments.service;

import com.example.autodoctorappointments.dto.AppointmentDto;
import com.example.autodoctorappointments.model.Appointment;
import com.example.autodoctorappointments.model.Doctor;
import com.example.autodoctorappointments.model.DoctorSchedule;
import com.example.autodoctorappointments.model.User;
import com.example.autodoctorappointments.repository.AppointmentRepository;
import com.example.autodoctorappointments.repository.DoctorRepository;
import com.example.autodoctorappointments.repository.DoctorScheduleRepository;
import com.example.autodoctorappointments.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorScheduleRepository scheduleRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository,
                              UserRepository userRepository, DoctorScheduleRepository scheduleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Appointment createAppointment(AppointmentDto appointmentDto) {
        User patient = userRepository.findById(appointmentDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Doctor doctor = doctorRepository.findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<DoctorSchedule> schedules = scheduleRepository.findByDoctorIdAndDate(
                doctor.getId(), appointmentDto.getDate());
        boolean isValidSlot = schedules.stream()
                .flatMap(s -> s.getTimeSlots().stream())
                .anyMatch(slot -> slot.equals(appointmentDto.getTime()));
        if (!isValidSlot) {
            throw new RuntimeException("Invalid time slot");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(appointmentDto.getDate());
        appointment.setTime(appointmentDto.getTime());
        appointment.setStatus(Appointment.Status.valueOf(appointmentDto.getStatus()));
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointments(Long userId) {
        return appointmentRepository.findByPatientId(userId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}