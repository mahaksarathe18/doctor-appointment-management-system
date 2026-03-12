package com.doctorservice.service;

import com.doctorservice.dto.ApiResponse;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.dto.SearchResultDto;
import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import com.doctorservice.entity.TimeSlots;
import com.doctorservice.repository.DoctorAppointmentScheduleRepository;
import com.doctorservice.repository.DoctorRepository;
import com.doctorservice.repository.TimeSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class SearchService {
    public SearchService(DoctorRepository doctorRepository, TimeSlotsRepository timeSlotsRepository,
                         DoctorAppointmentScheduleRepository doctorAppointmentScheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.timeSlotsRepository = timeSlotsRepository;

        this.doctorAppointmentScheduleRepository = doctorAppointmentScheduleRepository;
    }
    private DoctorRepository doctorRepository;
    private TimeSlotsRepository timeSlotsRepository;
    private final DoctorAppointmentScheduleRepository doctorAppointmentScheduleRepository;


    public ResponseEntity<ApiResponse<List<SearchResultDto>>> searchDoctors(String specialization, String areaName){
        LocalDate today= LocalDate.now();
        List<SearchResultDto> result = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findBySpecializationAndArea(specialization, areaName);

        for(Doctor doctor:doctors){
            SearchResultDto dto = new SearchResultDto();
            List<LocalDate> validDates = new ArrayList<>();

            // we have to save this on timeslots and then fetch the doctor appointments to get only future time
            // for each doctor we have a separate list
            List<LocalTime> allTimeSlots = new ArrayList<>();

            List<DoctorAppointmentSchedule> schedules = doctor.getAppointmentSchedules();

            for(DoctorAppointmentSchedule schedule:schedules) {
                LocalDate scheduleDate = schedule.getDate();
                LocalTime now = LocalTime.now();
               // List<TimeSlots> timeSlots = schedule.getTimeSlots(schedule.getId());
                // will increase the performance
                List<TimeSlots> timeSlots = timeSlotsRepository.getAllTimeSlots(schedule.getId());

                for(TimeSlots ts:timeSlots){
                    LocalTime slotTime = ts.getTime();
                    // if schedule is today -> only future times
                    if(scheduleDate.isEqual(today)){
                        if(slotTime.isAfter(now)){
                            validDates.add(scheduleDate);
                           allTimeSlots.add(slotTime);
                        }
                        // if schedule is in the future -> add all times
                    }else if(scheduleDate.isAfter(today)){
                                validDates.add(scheduleDate);
                                allTimeSlots.add(slotTime);

                    }

                }
            }
            dto.setId(doctor.getId());
            dto.setState(doctor.getState());
            dto.setName(doctor.getName());
            dto.setArea(doctor.getArea());
            dto.setCity(doctor.getCity());
            dto.setContact(doctor.getContact());
            dto.setExperience(doctor.getExperience());
            dto.setAddress(doctor.getAddress());
            dto.setQualification(doctor.getQualification());
            dto.setSpecialization(doctor.getSpecialization());

//                    DoctorAppointmentSchedule appointment = new DoctorAppointmentSchedule();
//                    appointment.setTimeSlots(allTimeSlots);
//                    for(LocalDate date: validDates){
//                        appointment.setDate(date);
//                    }
//
//            dto.setAppointmentSchedules();
            dto.setDates(validDates);
            dto.setTime(allTimeSlots);
            result.add(dto);

        }
        ApiResponse<List<SearchResultDto>> response = new ApiResponse<>();
        response.setMessage("Here the list of Doctors");
        response.setStatus(202);
        response.setData(result);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));

    }


//    public Doctor getDoctorById(long id) {
//       return doctorRepository.findById(id).get();
//    }
    }

