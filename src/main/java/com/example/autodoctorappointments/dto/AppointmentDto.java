package com.example.autodoctorappointments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Time is required")
    private String time;

    @NotBlank(message = "Status is required")
    private String status;
}