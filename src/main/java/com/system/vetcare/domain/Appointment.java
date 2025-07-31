package com.system.vetcare.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "veterinarian_id", referencedColumnName = "id")
    private Veterinarian veterinarian;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;
    
    @Column(name = "visit_date")
    private LocalDate visitDate;
    
    @Column(name = "visit_time")
    private LocalTime visitTime;
    
    private String room;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_visit")
    private EAppointmentType typeOfVisit;
    
    @Enumerated(EnumType.STRING)
    private EAppointmentStatus status;
    
}