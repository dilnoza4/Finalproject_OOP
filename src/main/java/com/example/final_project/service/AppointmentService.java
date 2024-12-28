package com.example.final_project.service;

import com.example.final_project.dto.AppointmentRequest;
import com.example.final_project.dto.AppointmentResponse;
import com.example.final_project.model.Appointment;
import com.example.final_project.model.AppointmentStatus;
import com.example.final_project.model.User;
import com.example.final_project.repository.AppointmentRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request, String userEmail) {
        validateAppointmentTime(request.getAppointmentDateTime());

        User student = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (appointmentRepository.existsByAppointmentDateTime(request.getAppointmentDateTime())) {
            throw new RuntimeException("This time slot is already booked");
        }

        Appointment appointment = Appointment.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .appointmentDateTime(request.getAppointmentDateTime())
                .serviceType(request.getServiceType())
                .concerns(request.getConcerns())
                .status(AppointmentStatus.PENDING)
                .student(student)
                .build();

        appointment = appointmentRepository.save(appointment);
        sendConfirmationEmail(appointment);

        return convertToResponse(appointment);
    }

    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId, String reason, String userEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getStudent().getEmail().equals(userEmail)) {
            throw new RuntimeException("Not authorized to cancel this appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment is already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment = appointmentRepository.save(appointment);
        sendCancellationEmail(appointment, reason);

        return convertToResponse(appointment);
    }

    public List<AppointmentResponse> getAvailableSlots(LocalDateTime start, LocalDateTime end) {
        List<Appointment> bookedSlots = appointmentRepository.findAppointmentsBetween(start, end);
        return bookedSlots.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private void validateAppointmentTime(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour < 9 || hour >= 17) {
            throw new RuntimeException("Appointments are only available between 9 AM and 5 PM");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot book appointments in the past");
        }
    }

    private void sendConfirmationEmail(Appointment appointment) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", appointment.getName());
        templateModel.put("dateTime", formatDateTime(appointment.getAppointmentDateTime()));
        templateModel.put("serviceType", appointment.getServiceType());
        emailService.sendAppointmentConfirmation(appointment.getEmail(), templateModel);
    }

    private void sendCancellationEmail(Appointment appointment, String reason) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", appointment.getName());
        templateModel.put("dateTime", formatDateTime(appointment.getAppointmentDateTime()));
        templateModel.put("reason", reason);
        emailService.sendAppointmentCancellation(appointment.getEmail(), templateModel);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a"));
    }

    private AppointmentResponse convertToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setName(appointment.getName());
        response.setEmail(appointment.getEmail());
        response.setPhone(appointment.getPhone());
        response.setAppointmentDateTime(appointment.getAppointmentDateTime());
        response.setServiceType(appointment.getServiceType());
        response.setStatus(appointment.getStatus());
        if (appointment.getCounselor() != null) {
            response.setCounselorName(appointment.getCounselor().getName());
        }
        return response;
    }
}