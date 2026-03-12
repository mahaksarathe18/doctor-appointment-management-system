package com.paymentservice.dto;

import lombok.Data;
@Data
public class StripeResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}