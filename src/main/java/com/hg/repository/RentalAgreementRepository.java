package com.hg.repository;

import com.hg.domain.RentalAgreement;
import com.hg.domain.Tenant;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RentalAgreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {
    Optional<RentalAgreement> findByStatusAndTenant(RentalAgreementStatusEnum status, Tenant tenant);
}
