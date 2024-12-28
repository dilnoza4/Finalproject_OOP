package com.example.final_project.dto;

import com.example.final_project.model.AppointmentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime appointmentDateTime;
    private String serviceType;
    private AppointmentStatus status;
    private String counselorName;
}