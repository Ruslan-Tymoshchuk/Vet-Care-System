package com.system.vetcare.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.system.vetcare.domain.enums.EEducation;
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
@Table(name = "staffs")
public class Staff {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @OneToOne(cascade = ALL)
    private User user;
    
    @Column(name = "total_months_of_experience")
    private Integer totalMonthsOfExperience;
    
    @Enumerated(STRING)
    @Column(name = "education_level")
    private EEducation educationLevel;
    
    @OneToMany(mappedBy = "staff")
    private Set<Leave> leaves;
    
}