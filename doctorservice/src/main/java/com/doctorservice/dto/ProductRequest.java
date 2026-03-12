package com.doctorservice.dto;

import lombok.Data;

@Data
public class ProductRequest {

    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
    private long bookingId;


}
