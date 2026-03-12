package com.doctorservice.dto;

import com.doctorservice.entity.Area;
import com.doctorservice.entity.City;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import com.doctorservice.entity.State;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class SearchResultDto {
        private Long id;
        private String name;
        private String specialization;
        private String qualification;
        private String contact;
        private String experience;
       // private String url;
        private State state;
        private City city;
        private String address;
        private Area area;
        private List<LocalDate> dates;
        private List<LocalTime> time;
        //private List<DoctorAppointmentSchedule> appointmentSchedules;
        private long consultationFee;


}
