package com.doctorservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({ "status", "message", "data" })
@Data
public class ApiResponse <T>{
    private String message;
    private int status;
    private T data;


}
