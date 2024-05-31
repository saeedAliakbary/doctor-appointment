package com.blubank.doctorappointment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
