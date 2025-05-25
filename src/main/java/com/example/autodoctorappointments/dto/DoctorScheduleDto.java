package com.example.autodoctorappointments.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DoctorScheduleDto {
    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotEmpty(message = "Time slots are required")
    private List<String> timeSlots;
}