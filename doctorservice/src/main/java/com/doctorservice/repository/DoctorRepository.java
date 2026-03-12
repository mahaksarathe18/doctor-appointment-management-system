package com.doctorservice.repository;

import com.doctorservice.dto.SearchResultDto;
import com.doctorservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Search by specialization + city name (case-insensitive)
    // will fetch all the details
    @Query("SELECT d FROM Doctor d " +
            "WHERE LOWER(d.specialization) = LOWER(:specialization) " +
            "AND LOWER(d.area.name) = LOWER(:areaName)")
    List<Doctor> findBySpecializationAndArea(@Param("specialization") String specialization,
                                                      @Param("areaName") String areaName);

    Optional<Doctor> findByName(String name);

}