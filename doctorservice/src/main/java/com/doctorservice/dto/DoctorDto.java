package com.doctorservice.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class DoctorDto {
        private String name;
        private String specialization;
        private String qualification;
        private String contact;
        private String experience;
        private String url;
        private String state;
        private String city;
        private String address;
        private String area;
        private LocalDate date;
        private LocalTime timeslot;
        private long consultationFee;


}
