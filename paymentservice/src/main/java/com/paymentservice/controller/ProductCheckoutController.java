package com.paymentservice.controller;

import com.paymentservice.clients.BookingClient;
import com.paymentservice.dto.BookingConfirmation;
import com.paymentservice.dto.ProductRequest;
import com.paymentservice.dto.StripeResponse;
import com.paymentservice.service.ProductCheckoutService;
import com.paymentservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController  {


    private ProductCheckoutService productCheckoutService;
    public ProductCheckoutController(ProductCheckoutService productCheckoutService) {
        this.productCheckoutService = productCheckoutService;
    }

    //http://localhost:8083/product/v1/checkout
    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        ResponseEntity<StripeResponse> stripeResponse = productCheckoutService.checkoutProducts(productRequest);
        return stripeResponse;
    }

    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(@RequestParam("session_id") String sessionId,
                                                @RequestParam("bookingId") long bookingId) {
        ResponseEntity<String> response = productCheckoutService.handleSuccess(sessionId, bookingId);
        return response;
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> handleCancel(){
        System.out.println("payment cancelled: false");
        return ResponseEntity.ok("Payment cancelled");
    }
}