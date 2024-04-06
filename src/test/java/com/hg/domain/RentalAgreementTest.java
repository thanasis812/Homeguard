package com.hg.domain;

import static com.hg.domain.LandLordTestSamples.*;
import static com.hg.domain.PaymentTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static com.hg.domain.RentalAgreementTestSamples.*;
import static com.hg.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RentalAgreementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentalAgreement.class);
        RentalAgreement rentalAgreement1 = getRentalAgreementSample1();
        RentalAgreement rentalAgreement2 = new RentalAgreement();
        assertThat(rentalAgreement1).isNotEqualTo(rentalAgreement2);

        rentalAgreement2.setId(rentalAgreement1.getId());
        assertThat(rentalAgreement1).isEqualTo(rentalAgreement2);

        rentalAgreement2 = getRentalAgreementSample2();
        assertThat(rentalAgreement1).isNotEqualTo(rentalAgreement2);
    }

    @Test
    void paymentTest() throws Exception {
        RentalAgreement rentalAgreement = getRentalAgreementRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        rentalAgreement.addPayment(paymentBack);
        assertThat(rentalAgreement.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getRentalAgreement()).isEqualTo(rentalAgreement);

        rentalAgreement.removePayment(paymentBack);
        assertThat(rentalAgreement.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getRentalAgreement()).isNull();

        rentalAgreement.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(rentalAgreement.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getRentalAgreement()).isEqualTo(rentalAgreement);

        rentalAgreement.setPayments(new HashSet<>());
        assertThat(rentalAgreement.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getRentalAgreement()).isNull();
    }

    @Test
    void tenantTest() throws Exception {
        RentalAgreement rentalAgreement = getRentalAgreementRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        rentalAgreement.setTenant(tenantBack);
        assertThat(rentalAgreement.getTenant()).isEqualTo(tenantBack);

        rentalAgreement.tenant(null);
        assertThat(rentalAgreement.getTenant()).isNull();
    }

    @Test
    void propertyOwnerTest() throws Exception {
        RentalAgreement rentalAgreement = getRentalAgreementRandomSampleGenerator();
        LandLord landLordBack = getLandLordRandomSampleGenerator();

        rentalAgreement.setPropertyOwner(landLordBack);
        assertThat(rentalAgreement.getPropertyOwner()).isEqualTo(landLordBack);

        rentalAgreement.propertyOwner(null);
        assertThat(rentalAgreement.getPropertyOwner()).isNull();
    }

    @Test
    void propertyTest() throws Exception {
        RentalAgreement rentalAgreement = getRentalAgreementRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        rentalAgreement.setProperty(propertyBack);
        assertThat(rentalAgreement.getProperty()).isEqualTo(propertyBack);

        rentalAgreement.property(null);
        assertThat(rentalAgreement.getProperty()).isNull();
    }
}
