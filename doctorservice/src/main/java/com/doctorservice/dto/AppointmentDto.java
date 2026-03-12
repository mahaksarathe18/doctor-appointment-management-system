package com.doctorservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;;
@Data
public class AppointmentDto {

    private String name;
    private LocalDate date;
    private LocalTime timeslot;


}
