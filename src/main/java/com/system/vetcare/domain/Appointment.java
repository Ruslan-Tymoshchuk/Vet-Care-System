package com.system.vetcare.domain;

import java.time.LocalDate;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.system.vetcare.domain.enums.EAppointmentStatus;
import com.system.vetcare.domain.enums.EAppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_slot_id", referencedColumnName = "id")
    private AppointmentTimeSlot timeSlot;
    
    private String room;
    
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_visit")
    private EAppointmentType typeOfVisit;
    
    @Enumerated(EnumType.STRING)
    private EAppointmentStatus status;
    
}