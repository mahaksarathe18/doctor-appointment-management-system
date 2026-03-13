package com.doctorservice.dto;

import jakarta.persistence.*;
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