package com.system.vetcare.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.EnumType.STRING;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.system.vetcare.domain.enums.ELeaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @Column(name = "begin_date")
    private LocalDate beginDate;
    
    @Column(name = "complete_date")
    private LocalDate completeDate;
    
    @Enumerated(STRING)
    @Column(name = "leave_type")
    private ELeaveType leaveType;
    
}