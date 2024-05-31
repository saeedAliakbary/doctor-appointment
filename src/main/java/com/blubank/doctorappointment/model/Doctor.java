package com.blubank.doctorappointment.model;

import lombok.Data;
import java.util.List;

@Data
public class Doctor {
    private Long id;
    private String name;
    private List<Appointment> appointments;
}
