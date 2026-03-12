package com.doctorservice.repository;

import com.doctorservice.entity.Doctor;
import com.doctorservice.entity.DoctorAppointmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DoctorAppointmentScheduleRepository extends JpaRepository<DoctorAppointmentSchedule, Long> {

      Optional<DoctorAppointmentSchedule> findByDate(LocalDate date);
      //boolean existsByDate(LocalDate date);
}