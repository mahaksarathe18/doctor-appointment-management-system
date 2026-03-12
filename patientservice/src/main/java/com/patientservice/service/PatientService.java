package com.patientservice.service;

import com.patientservice.dto.ApiResponseDto;
import com.patientservice.dto.PatientDto;
import com.patientservice.entity.Patient;
import com.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    private PatientRepository patientRepository;

    public ResponseEntity<ApiResponseDto<?>>register(PatientDto patientDto) {

        try{
            ApiResponseDto<Patient> response = new ApiResponseDto<>();
            Patient patient = new Patient();
            patient.setName(patientDto.getName());
            patient.setEmail(patientDto.getEmail());
            patient.setContact(patientDto.getContact());
            Patient savePatient = patientRepository.save(patient);

            response.setMessage("Patient get saved");
            response.setStatus(201);
            response.setData(savePatient);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
        }catch(Exception e){
            ApiResponseDto<String> response = new ApiResponseDto<>();
            response.setMessage("error");
            response.setStatus(404);
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
        }

    }

    public ResponseEntity<ApiResponseDto<String>> delete(String name) {
        ApiResponseDto<String> response = new ApiResponseDto<>();
        try{
            Patient patient = patientRepository.findByName(name).get();
            response.setMessage("Deleted");
            response.setStatus(201);
            response.setData("Patient deleted Successfully");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }catch(Exception e){
        response.setMessage("error");
        response.setStatus(404);
        response.setData(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
    }


    public Patient getPatientById(long id){
        return patientRepository.findById(id).get();
    }



}
