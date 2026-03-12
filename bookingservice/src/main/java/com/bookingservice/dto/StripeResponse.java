package com.bookingservice.dto;

import lombok.Data;

@Data
public class StripeResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}