package com.system.vetcare.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "veterinarians")
public class Veterinarian {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;
    
    @OneToMany(mappedBy = "veterinarian")
    private Set<Pet> pets;
    
    @OneToMany(mappedBy = "veterinarian")
    private Set<Appointment> appointments;
    
}