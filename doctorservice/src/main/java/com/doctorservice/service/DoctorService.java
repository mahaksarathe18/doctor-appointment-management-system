package com.doctorservice.service;

import com.doctorservice.dto.ApiResponse;
import com.doctorservice.dto.AppointmentDto;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.entity.*;
import com.doctorservice.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private DoctorRepository doctorRepository;
    private StateRepository stateRepository;
    private CityRepository cityRepository;
    private AreaRepository areaRepository;
    private TimeSlotsRepository timeSlotsRepository;
    private DoctorAppointmentScheduleRepository doctorAppointmentScheduleRepository;

    public DoctorService(DoctorRepository doctorRepository, StateRepository stateRepository, CityRepository cityRepository, AreaRepository areaRepository, TimeSlotsRepository timeSlotsRepository, DoctorAppointmentScheduleRepository doctorAppointmentScheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.areaRepository = areaRepository;
        this.timeSlotsRepository = timeSlotsRepository;
        this.doctorAppointmentScheduleRepository = doctorAppointmentScheduleRepository;
    }
    @Transactional
    public ResponseEntity<ApiResponse<String>> registerDoctor(DoctorDto doctorDto) {
        ApiResponse<String> response = new ApiResponse<>();

        // save the details
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setQualification(doctorDto.getQualification());
        doctor.setContact(doctorDto.getContact());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setUrl(doctorDto.getUrl());
        doctor.setAddress(doctorDto.getAddress());
        //converting into paise
        long fee = doctorDto.getConsultationFee() * 100l;
        doctor.setConsultationFee(fee);

        try {
            State state = stateRepository
                    .findByName(doctorDto.getState())
                    .orElseGet(() -> {
                        State newState = new State();
                        newState.setName(doctorDto.getState());
                        return stateRepository.save(newState);
                    });

            City city = cityRepository
                    .findByName(doctorDto.getCity())
                    .orElseGet(() -> {
                        City newCity = new City();
                        newCity.setName(doctorDto.getCity());
                        return cityRepository.save(newCity);
                    });

            Area area = areaRepository
                    .findByName(doctorDto.getArea())
                    .orElseGet(() -> {
                        Area newArea = new Area();
                        newArea.setName(doctorDto.getArea());
                        return areaRepository.save(newArea);
                    });

           // set other doctor fields here
            doctor.setState(state);
            doctor.setCity(city);
            doctor.setArea(area);
            doctorRepository.save(doctor); // cascades everything

            response.setStatus(200);
            response.setMessage("Doctor registered successfully");
            response.setData("SUCCESS");


        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error while registering doctor");
            response.setData(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));

    }
            @Transactional
            public ResponseEntity<ApiResponse<String>> addAppointments(AppointmentDto appointmentDto){
                ApiResponse<String> response = new ApiResponse<>();
                //Doctor doctor = new Doctor();
                Doctor doctor = doctorRepository.findByName(appointmentDto.getName()).get();
                try{
                    DoctorAppointmentSchedule schedule = doctorAppointmentScheduleRepository.findByDate(appointmentDto.getDate())
                                    .orElseGet(() -> {
                                        DoctorAppointmentSchedule newSchedule = new DoctorAppointmentSchedule();
                                        newSchedule.setDate(appointmentDto.getDate());
                                        newSchedule.setDoctor(doctor);
                                        return doctorAppointmentScheduleRepository.save(newSchedule);
                                    });

            TimeSlots slots = new TimeSlots();
            slots.setTime(appointmentDto.getTimeslot());
            slots.setDoctorAppointmentSchedule(schedule);
            timeSlotsRepository.save(slots);

            doctorRepository.save(doctor);

            response.setMessage("saved");
            response.setStatus(201);
            response.setData("Appointments get saved");

        }catch (Exception e){
            response.setMessage("error");
            response.setStatus(404);
            response.setData(e.getMessage());
        }
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
    }
    }

