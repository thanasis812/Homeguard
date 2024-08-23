package com.hg.domain;

import static com.hg.domain.ImageTestSamples.getImageRandomSampleGenerator;
import static com.hg.domain.LandLordTestSamples.*;
import static com.hg.domain.PropertyTestSamples.getPropertyRandomSampleGenerator;
import static com.hg.domain.RentalAgreementTestSamples.getRentalAgreementRandomSampleGenerator;
import static com.hg.domain.ReviewTestSamples.getReviewRandomSampleGenerator;
import static com.hg.domain.TenantTestSamples.getTenantRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LandLordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandLord.class);
        LandLord landLord1 = getLandLordSample1();
        LandLord landLord2 = new LandLord();
        assertThat(landLord1).isNotEqualTo(landLord2);

        landLord2.setId(landLord1.getId());
        assertThat(landLord1).isEqualTo(landLord2);

        landLord2 = getLandLordSample2();
        assertThat(landLord1).isNotEqualTo(landLord2);
    }

    @Test
    void ownerTest() throws Exception {
        LandLord landLord = getLandLordRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        landLord.setOwner(tenantBack);
        assertThat(landLord.getOwner()).isEqualTo(tenantBack);

        landLord.owner(null);
        assertThat(landLord.getOwner()).isNull();
    }

    @Test
    void landLordImageTest() throws Exception {
        LandLord landLord = getLandLordRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        landLord.setLandLordImage(imageBack);
        assertThat(landLord.getLandLordImage()).isEqualTo(imageBack);

        landLord.landLordImage(null);
        assertThat(landLord.getLandLordImage()).isNull();
    }

    @Test
    void propertysTest() throws Exception {
        LandLord landLord = getLandLordRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        landLord.addPropertys(propertyBack);
        assertThat(landLord.getPropertys()).containsOnly(propertyBack);
        assertThat(propertyBack.getLandLord()).isEqualTo(landLord);

        landLord.removePropertys(propertyBack);
        assertThat(landLord.getPropertys()).doesNotContain(propertyBack);
        assertThat(propertyBack.getLandLord()).isNull();

        landLord.propertys(new HashSet<>(Set.of(propertyBack)));
        assertThat(landLord.getPropertys()).containsOnly(propertyBack);
        assertThat(propertyBack.getLandLord()).isEqualTo(landLord);

        landLord.setPropertys(new HashSet<>());
        assertThat(landLord.getPropertys()).doesNotContain(propertyBack);
        assertThat(propertyBack.getLandLord()).isNull();
    }

    @Test
    void tenantReviewTest() throws Exception {
        LandLord landLord = getLandLordRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        landLord.addTenantReview(reviewBack);
        assertThat(landLord.getTenantReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getLandLord()).isEqualTo(landLord);

        landLord.removeTenantReview(reviewBack);
        assertThat(landLord.getTenantReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getLandLord()).isNull();

        landLord.tenantReviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(landLord.getTenantReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getLandLord()).isEqualTo(landLord);

        landLord.setTenantReviews(new HashSet<>());
        assertThat(landLord.getTenantReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getLandLord()).isNull();
    }

    @Test
    void rentalAgreementTest() throws Exception {
        LandLord landLord = getLandLordRandomSampleGenerator();
        RentalAgreement rentalAgreementBack = getRentalAgreementRandomSampleGenerator();

        landLord.addRentalAgreement(rentalAgreementBack);
        assertThat(landLord.getRentalAgreements()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getPropertyOwner()).isEqualTo(landLord);

        landLord.removeRentalAgreement(rentalAgreementBack);
        assertThat(landLord.getRentalAgreements()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getPropertyOwner()).isNull();

        landLord.rentalAgreements(new HashSet<>(Set.of(rentalAgreementBack)));
        assertThat(landLord.getRentalAgreements()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getPropertyOwner()).isEqualTo(landLord);

        landLord.setRentalAgreements(new HashSet<>());
        assertThat(landLord.getRentalAgreements()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getPropertyOwner()).isNull();
    }
}
