package com.bookingservice.dto;

import lombok.Data;

import java.time.LocalTime;
@Data
public class TimeSlots {
    private Long id;
    private LocalTime time;
    private DoctorAppointmentSchedule doctorAppointmentSchedule;

}