package com.hg.repository;

import com.hg.domain.HouseCharacteristics;
import com.hg.domain.Property;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HouseCharacteristics entity.
 */
@Repository
public interface HouseCharacteristicsRepository extends JpaRepository<HouseCharacteristics, Long> {
    @Query(
        "SELECT hc FROM HouseCharacteristics hc JOIN hc.property p WHERE p.id = :propertyId AND hc.group = :group AND hc.deleted = false"
    )
    List<HouseCharacteristicsDTO> findHouseCharacteristicsByPropertyAndGroup(
        @Param("propertyId") Long propertyId,
        @Param("group") HouseCharacteristicsGroupEnum group
    );
}
