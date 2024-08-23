package com.hg.repository;

import com.hg.domain.LandLord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LandLord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandLordRepository extends JpaRepository<LandLord, Long> {}
