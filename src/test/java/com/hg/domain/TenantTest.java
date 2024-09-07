package com.hg.domain;

import static com.hg.domain.ImageTestSamples.*;
import static com.hg.domain.LandLordTestSamples.*;
import static com.hg.domain.LocationTestSamples.*;
import static com.hg.domain.RentalAgreementTestSamples.*;
import static com.hg.domain.ReviewTestSamples.*;
import static com.hg.domain.TenantPropertyPreferencesTestSamples.*;
import static com.hg.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TenantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tenant.class);
        Tenant tenant1 = getTenantSample1();
        Tenant tenant2 = new Tenant();
        assertThat(tenant1).isNotEqualTo(tenant2);

        tenant2.setId(tenant1.getId());
        assertThat(tenant1).isEqualTo(tenant2);

        tenant2 = getTenantSample2();
        assertThat(tenant1).isNotEqualTo(tenant2);
    }

    @Test
    void locationTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        tenant.setLocation(locationBack);
        assertThat(tenant.getLocation()).isEqualTo(locationBack);

        tenant.location(null);
        assertThat(tenant.getLocation()).isNull();
    }

    @Test
    void tenantImageTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        tenant.setTenantImage(imageBack);
        assertThat(tenant.getTenantImage()).isEqualTo(imageBack);

        tenant.tenantImage(null);
        assertThat(tenant.getTenantImage()).isNull();
    }

    @Test
    void propertyPreferencesTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        TenantPropertyPreferences tenantPropertyPreferencesBack = getTenantPropertyPreferencesRandomSampleGenerator();

        tenant.addPropertyPreferences(tenantPropertyPreferencesBack);
        assertThat(tenant.getPropertyPreferences()).containsOnly(tenantPropertyPreferencesBack);
        assertThat(tenantPropertyPreferencesBack.getTenant()).isEqualTo(tenant);

        tenant.removePropertyPreferences(tenantPropertyPreferencesBack);
        assertThat(tenant.getPropertyPreferences()).doesNotContain(tenantPropertyPreferencesBack);
        assertThat(tenantPropertyPreferencesBack.getTenant()).isNull();

        tenant.propertyPreferences(new HashSet<>(Set.of(tenantPropertyPreferencesBack)));
        assertThat(tenant.getPropertyPreferences()).containsOnly(tenantPropertyPreferencesBack);
        assertThat(tenantPropertyPreferencesBack.getTenant()).isEqualTo(tenant);

        tenant.setPropertyPreferences(new HashSet<>());
        assertThat(tenant.getPropertyPreferences()).doesNotContain(tenantPropertyPreferencesBack);
        assertThat(tenantPropertyPreferencesBack.getTenant()).isNull();
    }

    @Test
    void apartmentReviewTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        tenant.addApartmentReview(reviewBack);
        assertThat(tenant.getApartmentReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getTenant()).isEqualTo(tenant);

        tenant.removeApartmentReview(reviewBack);
        assertThat(tenant.getApartmentReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getTenant()).isNull();

        tenant.apartmentReviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(tenant.getApartmentReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getTenant()).isEqualTo(tenant);

        tenant.setApartmentReviews(new HashSet<>());
        assertThat(tenant.getApartmentReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getTenant()).isNull();
    }

    @Test
    void rentedPropertysAgreementTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        RentalAgreement rentalAgreementBack = getRentalAgreementRandomSampleGenerator();

        tenant.addRentedPropertysAgreement(rentalAgreementBack);
        assertThat(tenant.getRentedPropertysAgreements()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getTenant()).isEqualTo(tenant);

        tenant.removeRentedPropertysAgreement(rentalAgreementBack);
        assertThat(tenant.getRentedPropertysAgreements()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getTenant()).isNull();

        tenant.rentedPropertysAgreements(new HashSet<>(Set.of(rentalAgreementBack)));
        assertThat(tenant.getRentedPropertysAgreements()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getTenant()).isEqualTo(tenant);

        tenant.setRentedPropertysAgreements(new HashSet<>());
        assertThat(tenant.getRentedPropertysAgreements()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getTenant()).isNull();
    }

    @Test
    void landLordTest() throws Exception {
        Tenant tenant = getTenantRandomSampleGenerator();
        LandLord landLordBack = getLandLordRandomSampleGenerator();

        tenant.setLandLord(landLordBack);
        assertThat(tenant.getLandLord()).isEqualTo(landLordBack);
        assertThat(landLordBack.getOwner()).isEqualTo(tenant);

        tenant.landLord(null);
        assertThat(tenant.getLandLord()).isNull();
        assertThat(landLordBack.getOwner()).isNull();
    }
}
