package com.bookingservice.dto;

import lombok.Data;

@Data
public class ApiResponseDto<T> {
    private String message ;
    private int status;
    private T data;


}
