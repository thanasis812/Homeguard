package com.hg.repository;

import com.hg.domain.TenantPropertyPreferences;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TenantPropertyPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantPropertyPreferencesRepository extends JpaRepository<TenantPropertyPreferences, Long> {
    @Query("SELECT tpp FROM TenantPropertyPreferences tpp where tpp.tenant.id= :tenantId ")
    List<TenantPropertyPreferences> findTenantPropertyPreferencesByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT tpp FROM TenantPropertyPreferences tpp where tpp.tenant.id= :tenantId and  tpp.property.id= :propertyId")
    Optional<TenantPropertyPreferences> findTenantPropertyPreferencesByTenantIdAndHouseId(
        @Param("tenantId") Long tenantId,
        @Param("propertyId") Long propertyId
    );
}
