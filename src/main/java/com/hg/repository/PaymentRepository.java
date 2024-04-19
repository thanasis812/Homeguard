package com.hg.repository;

import com.hg.domain.Payment;
import com.hg.domain.RentalAgreement;
import com.hg.domain.Tenant;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
