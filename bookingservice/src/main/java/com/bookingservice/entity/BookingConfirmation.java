package com.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Table(name="booking_confirmations")
public class BookingConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String doctorName;
    private String patientName;
    private String address;
    private LocalDate date;
    private LocalTime time;
    private boolean status;

}