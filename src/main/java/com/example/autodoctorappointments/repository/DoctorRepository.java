package com.example.autodoctorappointments.repository;

import com.example.autodoctorappointments.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}