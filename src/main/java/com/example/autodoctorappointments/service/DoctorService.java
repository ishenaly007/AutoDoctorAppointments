package com.example.autodoctorappointments.service;

import com.example.autodoctorappointments.dto.DoctorDto;
import com.example.autodoctorappointments.model.Appointment;
import com.example.autodoctorappointments.model.Doctor;
import com.example.autodoctorappointments.model.DoctorSchedule;
import com.example.autodoctorappointments.repository.AppointmentRepository;
import com.example.autodoctorappointments.repository.DoctorRepository;
import com.example.autodoctorappointments.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    public DoctorService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, DoctorScheduleRepository doctorScheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorScheduleRepository = doctorScheduleRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor createDoctor(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setExperience(doctorDto.getExperience());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor with ID " + id + " not found");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        appointmentRepository.deleteAll(appointments);

        List<DoctorSchedule> schedules = doctorScheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getDoctor().getId().equals(id))
                .toList();
        doctorScheduleRepository.deleteAll(schedules);

        doctorRepository.deleteById(id);
    }
}