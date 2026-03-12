package com.patientservice.controller;

import com.patientservice.dto.ApiResponseDto;
import com.patientservice.dto.PatientDto;
import com.patientservice.entity.Patient;
import com.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    private PatientService patientService;
  //http://localhost:8082/api/v1/patient/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<?>> register(@RequestBody PatientDto patientDto){
        ResponseEntity<ApiResponseDto<?>> patient = patientService.register(patientDto);
        return patient;
    }
    //http://localhost:8082/api/v1/patient/delete
    @PostMapping("/delete")
    public ResponseEntity<ApiResponseDto<String>> delete(@RequestParam  String name ){
        ResponseEntity<ApiResponseDto<String>> delete = patientService.delete(name);
        return delete;
    }

    @GetMapping("/getpatientbyid")
    public Patient getPatientById(@RequestParam long id){
        Patient patient = patientService.getPatientById(id);
         return patient;
    }

}