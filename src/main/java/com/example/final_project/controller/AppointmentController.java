package com.example.final_project.controller;

import com.example.final_project.dto.*;
import com.example.final_project.service.AppointmentService;
import com.example.final_project.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestBody AppointmentRequest request,
            @RequestHeader("Authorization") String token
    ) {
        String userEmail = jwtService.extractUsername(token.substring(7));
        return ResponseEntity.ok(appointmentService.createAppointment(request, userEmail));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id,
            @RequestParam(required = false) String reason,
            @RequestHeader("Authorization") String token
    ) {
        String userEmail = jwtService.extractUsername(token.substring(7));
        return ResponseEntity.ok(appointmentService.cancelAppointment(id, reason, userEmail));
    }

    @GetMapping("/available")
    public ResponseEntity<List<AppointmentResponse>> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(appointmentService.getAvailableSlots(start, end));
    }
}