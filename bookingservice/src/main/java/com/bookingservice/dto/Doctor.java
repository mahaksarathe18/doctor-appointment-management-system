package com.bookingservice.dto;

import lombok.Data;

import java.util.List;
@Data
public class Doctor {

    private Long id;
    private String name;
    private String specialization;
    private String qualification;
    private String contact;
    private String experience;
    private String url;
    private State state;
    private City city;
    private String address;
    private Area area;
    private List<DoctorAppointmentSchedule> appointmentSchedules;
    private long consultationFee;

}
