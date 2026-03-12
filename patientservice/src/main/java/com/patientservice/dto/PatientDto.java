package com.patientservice.dto;

import lombok.Data;

@Data
public class PatientDto {
    private Long id;
    private String name;
    private String email;
    private String contact;

}
