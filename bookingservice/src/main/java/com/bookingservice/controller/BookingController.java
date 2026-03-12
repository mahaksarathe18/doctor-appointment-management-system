package com.bookingservice.controller;


import com.bookingservice.dto.StripeResponse;
import com.bookingservice.entity.BookingConfirmation;
import com.bookingservice.service.BookingService;
import org.apache.coyote.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;
     BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Example: http://localhost:8085/api/v1/booking/getdoctor?doctorId=1&patientId=1&date=2026-02-18&time=14:00
    @GetMapping("/getdoctor")
    public ResponseEntity<StripeResponse> getDoctorById(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time

    ) {

        ResponseEntity<StripeResponse> patient = bookingService.getDoctorById(doctorId, patientId, date, time);
        return patient;
    }
    @GetMapping("/bookingId")
    public BookingConfirmation getBookingById(@RequestParam Long bookingId) {
        BookingConfirmation bookingById = bookingService.getBookingById(bookingId);
        return bookingById;
    }

    @PutMapping("/updatestatus")
    public void confirmBooking(
            @RequestBody BookingConfirmation bookingConfirmation) {
      bookingService.confirmBooking(bookingConfirmation);
    }

    @GetMapping ("/Send")
    @Scheduled(cron = "0 0 * * * *")
    public String sendSms(){
        bookingService.sendSms("+918827636483");
        return "Done";
    }

}