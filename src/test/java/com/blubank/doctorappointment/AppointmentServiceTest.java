package com.blubank.doctorappointment;

import com.blubank.doctorappointment.model.Appointment;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {
    private AppointmentRepository repository;
    private AppointmentService service;

    @BeforeEach
    public void setup() {
        repository = mock(AppointmentRepository.class);
        service = new AppointmentService(repository);
    }

    @Test
    public void testGetAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        when(repository.findAll()).thenReturn(appointments);

        List<Appointment> result = service.getAllAppointments();
        assertEquals(appointments, result);
    }

    @Test
    public void testGetAppointmentById() {
        Appointment appointment = new Appointment();
        when(repository.findById(1L)).thenReturn(Optional.of(appointment));

        Optional<Appointment> result = service.getAppointmentById(1L);
        assertEquals(Optional.of(appointment), result);
    }

    @Test
    public void testAddAppointment() {
        Appointment appointment = new Appointment();
        service.addAppointment(appointment);

        verify(repository, times(1)).save(appointment);
    }

    @Test
    public void testDeleteAppointment() {
        service.deleteAppointment(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testTakeAppointment_Success() {
        Long appointmentId = 1L;
        String patientName = "John Doe";
        String patientPhone = "1234567890";

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStartTime(LocalDateTime.now().plusDays(1));
        appointment.setEndTime(LocalDateTime.now().plusDays(1).plusMinutes(30));
        appointment.setTaken(false);

        when(repository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        String result = service.takeAppointment(appointmentId, patientName, patientPhone);

        assertEquals("Appointment successfully taken", result);
        assertTrue(appointment.isTaken());
        assertEquals(patientName, appointment.getPatientName());
        assertEquals(patientPhone, appointment.getPatientPhone());

        verify(repository, times(1)).save(appointment);
    }

    @Test
    public void testTakeAppointment_AppointmentNotFound() {
        Long appointmentId = 1L;
        String patientName = "John Doe";
        String patientPhone = "1234567890";

        when(repository.findById(appointmentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.takeAppointment(appointmentId, patientName, patientPhone)
        );

        assertEquals("Appointment does not exist", exception.getMessage());
        verify(repository, never()).save(any(Appointment.class));
    }

    @Test
    public void testTakeAppointment_AlreadyTaken() {
        Long appointmentId = 1L;
        String patientName = "John Doe";
        String patientPhone = "1234567890";

        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStartTime(LocalDateTime.now().plusDays(1));
        appointment.setEndTime(LocalDateTime.now().plusDays(1).plusMinutes(30));
        appointment.setTaken(true);

        when(repository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Exception exception = assertThrows(IllegalStateException.class, () ->
                service.takeAppointment(appointmentId, patientName, patientPhone)
        );

        assertEquals("Appointment is already taken", exception.getMessage());
        verify(repository, never()).save(any(Appointment.class));
    }

    @Test
    public void testTakeAppointment_MissingPatientInfo() {
        Long appointmentId = 1L;

        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                service.takeAppointment(appointmentId, null, "1234567890")
        );

        assertEquals("Patient name and phone number must be provided", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                service.takeAppointment(appointmentId, "John Doe", null)
        );

        assertEquals("Patient name and phone number must be provided", exception2.getMessage());
    }
}
