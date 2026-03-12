package com.doctorservice.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
// will help us to arrange the JSON response
@JsonPropertyOrder({
        "id",
        "name",
        "specialization",
        "qualification",
        "experience",
        "contact",
        "url",
        "address",
        "area",
        "city",
        "state",
        "appointmentSchedules",
        "consultationFee"
})
@Entity
@Setter
@Getter
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "qualification",nullable = false)
    private String qualification;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "experience", nullable = false)
    private String experience;

    @Column(name = "url", nullable = false, length = 2000)
    private String url;

    @Column(name= "consultation_fee",nullable = false)
    private long consultationFee;

    @ManyToOne
            //(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
            //(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address", nullable = false, length = 1000)
    private String address;

    @ManyToOne
            //(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id")
    private  Area area;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DoctorAppointmentSchedule> appointmentSchedules;


}