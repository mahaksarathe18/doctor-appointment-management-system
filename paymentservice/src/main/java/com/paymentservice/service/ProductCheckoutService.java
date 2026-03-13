package com.paymentservice.service;

import com.paymentservice.clients.BookingClient;
import com.paymentservice.dto.BookingConfirmation;
import com.paymentservice.dto.ProductRequest;
import com.paymentservice.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public class ProductCheckoutService {

    private StripeService stripeService;
    private BookingClient bookingClient;

    public ProductCheckoutService(StripeService stripeService, BookingClient bookingClient) {
        this.stripeService = stripeService;
        this.bookingClient = bookingClient;
    }

    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }


    public ResponseEntity<String> handleSuccess(@RequestParam("session_id") String sessionId,
                                                @RequestParam("bookingId") long bookingId) {
        //here we call the payment gateway with the help of sessionId for details about payment successful or not
        Stripe.apiKey = "xxxxxxxxxx";

        try {
            Session session = Session.retrieve(sessionId);
            String paymentStatus = session.getPaymentStatus();

            if ("paid".equalsIgnoreCase(paymentStatus)) {
                BookingConfirmation confirm = bookingClient.getBookingsById(bookingId);
                confirm.setStatus(true);
                bookingClient.confirmBooking(confirm);
                System.out.println("✅ Payment successful: true");
                return ResponseEntity.ok("Payment successful");
            } else {
                System.out.println("❌ Payment not completed: false");
                return ResponseEntity.status(400).body("Payment not completed");
            }

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Stripe error occurred");
        }
    }

}
