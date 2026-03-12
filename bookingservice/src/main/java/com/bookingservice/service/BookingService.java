package com.bookingservice.service;

import com.bookingservice.client.DoctorClient;
import com.bookingservice.client.PatientClient;
import com.bookingservice.client.PaymentClient;
import com.bookingservice.dto.*;
import com.bookingservice.entity.BookingConfirmation;
import com.bookingservice.repository.BookingConfirmationRepository;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {
    private DoctorClient doctorClient;
    private PatientClient patientClient;
    private PaymentClient paymentClient;
    private final BookingConfirmationRepository bookingConfirmationRepository;

    public BookingService(DoctorClient doctorClient, PatientClient patientClient, PaymentClient paymentClient,
                          BookingConfirmationRepository bookingConfirmationRepository) {
        this.doctorClient = doctorClient;
        this.patientClient = patientClient;
        this.paymentClient = paymentClient;
        this.bookingConfirmationRepository = bookingConfirmationRepository;
    }
@CircuitBreaker(name="bookingService",fallbackMethod = "fallback")
    public ResponseEntity<StripeResponse> getDoctorById(
             Long doctorId,
             Long patientId,
             LocalDate date,
             LocalTime time

    ) {

        Patient patient = patientClient.getPatientById(patientId);
        Doctor doctor = doctorClient.getDoctorById(doctorId);


        BookingConfirmation bookingConfirmation = new BookingConfirmation();
        bookingConfirmation.setDoctorName(doctor.getName());
        bookingConfirmation.setPatientName(patient.getName());
        bookingConfirmation.setAddress(doctor.getAddress());

        List<DoctorAppointmentSchedule> appointmentSchedules = doctor.getAppointmentSchedules();

        for (DoctorAppointmentSchedule app : appointmentSchedules) {
            LocalDate localDate = app.getDate();

            if (localDate.isEqual(date)) {
                List<TimeSlots> timeSlots = app.getTimeSlots();

                for (TimeSlots t : timeSlots) {
                    if (t.getTime().equals(time)) {
                        bookingConfirmation.setDate(date);
                        bookingConfirmation.setTime(time);
                        break;
                    }
                }
            }
        }

        //save booking confirmation
        BookingConfirmation savedBookingConfirmation =
                bookingConfirmationRepository.save(bookingConfirmation);

        ProductRequest pr = new ProductRequest();
        pr.setName(bookingConfirmation.getPatientName());
        pr.setAmount(doctor.getConsultationFee());
        pr.setCurrency("INR");
        pr.setQuantity(1L);
        pr.setBookingId(bookingConfirmation.getId());
        ResponseEntity<StripeResponse> payment = paymentClient.checkoutProducts(pr);
        //bookingConfirmation.setStatus(payment);
        // payment client intergration is left
        return payment;
    }

    public String fallback(Exception ex) {
        return "doctor and petient services in down state";
    }



    public BookingConfirmation getBookingById( Long bookingId) {
        return bookingConfirmationRepository
                .findById(bookingId)
                .get();
    }


    public void confirmBooking(
             BookingConfirmation bookingConfirmation) {
        bookingConfirmationRepository.save(bookingConfirmation);
    }

    @Value("${twilio.from-number}")
    private String fromNumber;

    public void sendSms(String to ){

        List<BookingConfirmation> allBooking = bookingConfirmationRepository.findAll();
        for(BookingConfirmation booking:allBooking){
            if(LocalDateTime.now().plusHours(1).equals(booking.getTime())){
                Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(fromNumber),
                        "Your booking = "+booking.getTime()
                ).create();
            }
        }
    }
}
//        Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber(fromNumber),
//                message
//        ).create();

