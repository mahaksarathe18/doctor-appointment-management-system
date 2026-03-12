package com.paymentservice.clients;

import com.paymentservice.dto.BookingConfirmation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "BOOKINGSERVICE")
public interface BookingClient {

    @GetMapping("/api/v1/booking/bookingId")
    BookingConfirmation getBookingsById(
            @RequestParam("bookingId") long bookingId);

    @PutMapping("/api/v1/booking/updatestatus")
   public void  confirmBooking(
            @RequestBody BookingConfirmation bookingConfirmation);
}

