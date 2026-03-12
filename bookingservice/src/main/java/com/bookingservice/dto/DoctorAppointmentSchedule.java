package com.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class DoctorAppointmentSchedule {

    private Long id;
    private Doctor doctor;
    private List<TimeSlots> timeSlots;
    private LocalDate date;


}
