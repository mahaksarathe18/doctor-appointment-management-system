package com.bookingservice.repository;

import com.bookingservice.entity.BookingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingConfirmationRepository extends JpaRepository<BookingConfirmation, Long> {


}