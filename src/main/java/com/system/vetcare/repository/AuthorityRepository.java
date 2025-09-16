package com.system.vetcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.system.vetcare.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {}