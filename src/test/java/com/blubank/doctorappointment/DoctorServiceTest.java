package com.blubank.doctorappointment;

import com.blubank.doctorappointment.model.Appointment;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    private AppointmentRepository repository;
    private DoctorService service;

    @BeforeEach
    public void setup() {
        repository = mock(AppointmentRepository.class);
        service = new DoctorService(repository);
    }

    @Test
    public void testAddOpenTimes() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        List<Appointment> appointments = service.addOpenTimes(startTime, endTime);

        verify(repository, times(4)).save(any(Appointment.class));
        assertEquals(4, appointments.size());
    }

    @Test
    public void testGetAvailableAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        when(repository.findAll()).thenReturn(appointments);

        List<Appointment> result = service.getAvailableAppointments();
        assertEquals(appointments, result);
    }
}
