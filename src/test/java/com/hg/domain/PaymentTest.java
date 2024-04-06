package com.hg.domain;

import static com.hg.domain.PaymentTestSamples.*;
import static com.hg.domain.RentalAgreementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void rentalAgreementTest() throws Exception {
        Payment payment = getPaymentRandomSampleGenerator();
        RentalAgreement rentalAgreementBack = getRentalAgreementRandomSampleGenerator();

        payment.setRentalAgreement(rentalAgreementBack);
        assertThat(payment.getRentalAgreement()).isEqualTo(rentalAgreementBack);

        payment.rentalAgreement(null);
        assertThat(payment.getRentalAgreement()).isNull();
    }
}
