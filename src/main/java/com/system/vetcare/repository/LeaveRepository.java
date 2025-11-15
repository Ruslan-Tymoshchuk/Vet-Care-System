package com.system.vetcare.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {

    @Query("""
              SELECT l
              FROM Leave l
              JOIN Veterinarian v ON v.staff = l.staff
              WHERE v.id = :veterinarianId
                AND l.beginDate <= :completeDate
                AND l.completeDate >= :beginDate
            """)
    List<Leave> findByVeterinarianInDateInterval(@Param("veterinarianId") Integer veterinarianId,
            @Param("beginDate") LocalDate beginDate, @Param("completeDate") LocalDate completeDate);

}