package com.doctorservice.controller;

import com.doctorservice.dto.ApiResponse;
import com.doctorservice.dto.AppointmentDto;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor/admin")
public class DocterController {

    private DoctorService doctorService;

    public DocterController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
//http://localhost:8081/api/v1/doctor/admin/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> RegisterDocter(@RequestBody DoctorDto doctorDto){

        ResponseEntity<ApiResponse<String>> response = doctorService.registerDoctor(doctorDto);
        return response;
    }
   // http://localhost:8081/api/v1/doctor/admin/appointments
    @PostMapping("/appointments")
    public ResponseEntity<ApiResponse<String>> addAppointments(@RequestBody AppointmentDto appointmentDto){
        ResponseEntity<ApiResponse<String>> response = doctorService.addAppointments(appointmentDto);
          return response;
    }


}
