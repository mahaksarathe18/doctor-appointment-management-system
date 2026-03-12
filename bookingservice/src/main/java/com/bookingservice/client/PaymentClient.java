package com.bookingservice.client;

import com.bookingservice.dto.ProductRequest;

import com.bookingservice.dto.StripeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentservice", url = "http://localhost:8083/product/v1")
public interface PaymentClient {
    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest);
}
