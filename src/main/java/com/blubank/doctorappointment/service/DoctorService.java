package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.model.Appointment;
import com.blubank.doctorappointment.model.Doctor;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private final AppointmentRepository appointmentRepository;

    public DoctorService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> addOpenTimes(LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> appointments = new ArrayList<>();
        while (startTime.plusMinutes(30).isBefore(endTime) || startTime.plusMinutes(30).equals(endTime)) {
            Appointment appointment = new Appointment();
            appointment.setStartTime(startTime);
            appointment.setEndTime(startTime.plusMinutes(30));
            appointmentRepository.save(appointment);
            appointments.add(appointment);
            startTime = startTime.plusMinutes(30);
        }
        return appointments;
    }

    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findAll();
    }

    // Additional methods as required by stories
}
