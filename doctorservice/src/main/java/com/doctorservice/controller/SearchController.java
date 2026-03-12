package com.doctorservice.controller;

import com.doctorservice.dto.ApiResponse;
import com.doctorservice.dto.DoctorDto;
import com.doctorservice.dto.SearchResultDto;
import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import com.doctorservice.entity.TimeSlots;
import com.doctorservice.repository.DoctorRepository;
import com.doctorservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class SearchController {

    private DoctorRepository doctorRepository;
    private SearchService searchService;

    public SearchController(DoctorRepository doctorRepository, SearchService searchService) {
        this.doctorRepository = doctorRepository;
        this.searchService = searchService;
    }

    // Example:
    // http://localhost:8081/api/v1/doctor/search?specialization=general&areaName=cant
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SearchResultDto>>> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String areaName
    ) {
        ResponseEntity<ApiResponse<List<SearchResultDto>>> Doctors = searchService.searchDoctors(specialization, areaName);
        return Doctors;
    }
    //http://localhost:8081/api/v1/doctor/getdoctorbyid?id=1
    @GetMapping("/getdoctorbyid")
    public Doctor getDoctorById(@RequestParam long id) {
        return doctorRepository.findById(id).get();
    }



//    public Doctor getDoctorById(
//            @RequestParam long id
//    ){
//        Doctor doctor = searchService.getDoctorById(id);
//        return doctor;
//    }
}