package com.example.final_project.repository;

import com.example.final_project.model.Appointment;
import com.example.final_project.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStudentEmail(String email);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime BETWEEN :start AND :end")
    List<Appointment> findAppointmentsBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    boolean existsByAppointmentDateTime(LocalDateTime dateTime);

    List<Appointment> findByStatusAndAppointmentDateTimeBetween(
            AppointmentStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}