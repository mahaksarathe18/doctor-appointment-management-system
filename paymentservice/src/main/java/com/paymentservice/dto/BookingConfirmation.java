package com.paymentservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class BookingConfirmation {
        private long id;
        private String doctorName;
        private String patientName;
        private String address;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private LocalDate date;
        private LocalTime time;
        private boolean status;


}
