package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.model.Appointment;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return repository.findById(id);
    }

    public List<Appointment> getAppointmentsByPatientPhone(String phone) {
        return repository.findByPatientPhone(phone);
    }

    public void addAppointment(Appointment appointment) {
        repository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public String takeAppointment(Long appointmentId, String patientName, String patientPhone) {
        if (patientName == null || patientPhone == null || patientPhone=="" || patientName=="") {
            return "Patient name and phone number must be provided";
        }
        Optional<Appointment> appointmentOptional = repository.findById(appointmentId);

        if (appointmentOptional.isEmpty()) {
            return "Appointments not ready to taking";
        }

        Appointment appointment = appointmentOptional.get();

        if (appointment.isTaken()) {
            return "Appointment has been taken! and you can not take it";
        }

        appointment.setPatientName(patientName);
        appointment.setPatientPhone(patientPhone);
        appointment.setTaken(true);

        repository.save(appointment);

        return "Appointment successfully taken";
    }
}
