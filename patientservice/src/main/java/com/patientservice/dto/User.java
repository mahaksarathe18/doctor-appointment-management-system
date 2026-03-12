package com.patientservice.dto;

import lombok.Data;

@Data
public class User {
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;

}