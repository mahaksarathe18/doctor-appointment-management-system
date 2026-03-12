package com.doctorservice.repository;

import com.doctorservice.entity.Area;
import com.doctorservice.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByName(String area);
}