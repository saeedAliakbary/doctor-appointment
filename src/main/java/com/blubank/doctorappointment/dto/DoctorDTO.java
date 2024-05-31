package com.blubank.doctorappointment.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private List<AppointmentDTO> appointments;
}
