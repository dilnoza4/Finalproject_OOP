package com.example.final_project.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private String name;
    private String email;
    private String phone;
    private LocalDateTime appointmentDateTime;
    private String serviceType;
    private String concerns;
}