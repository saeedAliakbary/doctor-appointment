package com.blubank.doctorappointment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TakeAppointmentRequest {
    @NotNull(message = "Appointment ID must not be null")
    private Long appointmentId;

    @NotBlank(message = "Patient name must not be blank")
    private String patientName;

    @NotBlank(message = "Patient phone must not be blank")
    private String patientPhone;
}
