package com.hg.repository;

import com.hg.domain.Property;
import com.hg.domain.RentalAgreement;
import com.hg.domain.Tenant;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RentalAgreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {
    Optional<RentalAgreement> findByStatusAndTenant(RentalAgreementStatusEnum status, Tenant tenant);

    @Query("SELECT tpp FROM RentalAgreement tpp where tpp.tenant.id= :tenantId and  tpp.status= :status")
    Optional<RentalAgreement> findByStatusAndTenant(RentalAgreementStatusEnum status, Long tenantId);

    Optional<RentalAgreement> findByTenantAndProperty(Tenant tenant, Property property);
    Optional<RentalAgreement> findFirstByPropertyOrderByCreatedDateDesc(Property property);
}
