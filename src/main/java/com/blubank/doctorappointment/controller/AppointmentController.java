package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.dto.TakeAppointmentRequest;
import com.blubank.doctorappointment.model.Appointment;
import com.blubank.doctorappointment.service.AppointmentService;
import com.blubank.doctorappointment.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Optional<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/patient/{phone}")
    public List<Appointment> getAppointmentsByPatientPhone(@PathVariable String phone) {
        return appointmentService.getAppointmentsByPatientPhone(phone);
    }

    @PostMapping
    public void addAppointment(@RequestBody Appointment appointment) {
        appointmentService.addAppointment(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @PostMapping("/open-times")
    public List<Appointment> addOpenTimes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return doctorService.addOpenTimes(startTime, endTime);

    }
    @PostMapping("/take")
    public String takeAppointment(@Valid @RequestBody TakeAppointmentRequest request) {
        return appointmentService.takeAppointment(request.getAppointmentId(), request.getPatientName(), request.getPatientPhone());

    }
}
