package com.example.final_project.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RescheduleRequest {
    private LocalDateTime newDateTime;
}