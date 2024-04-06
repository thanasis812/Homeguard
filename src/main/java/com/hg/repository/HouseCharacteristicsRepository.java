package com.hg.repository;

import com.hg.domain.HouseCharacteristics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HouseCharacteristics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseCharacteristicsRepository extends JpaRepository<HouseCharacteristics, Long> {}
