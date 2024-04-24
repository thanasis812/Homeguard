package com.hg.domain;

import static com.hg.domain.HouseCharacteristicsTestSamples.*;
import static com.hg.domain.ImageTestSamples.*;
import static com.hg.domain.LandLordTestSamples.*;
import static com.hg.domain.LocationTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static com.hg.domain.RentalAgreementTestSamples.*;
import static com.hg.domain.ReviewTestSamples.*;
import static com.hg.domain.TenantPropertyPreferencesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = getPropertySample1();
        Property property2 = new Property();
        assertThat(property1).isNotEqualTo(property2);

        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);

        property2 = getPropertySample2();
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    void locationTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        property.setLocation(locationBack);
        assertThat(property.getLocation()).isEqualTo(locationBack);

        property.location(null);
        assertThat(property.getLocation()).isNull();
    }

    @Test
    void rentalTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        RentalAgreement rentalAgreementBack = getRentalAgreementRandomSampleGenerator();

        property.addRental(rentalAgreementBack);
        assertThat(property.getRentals()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getProperty()).isEqualTo(property);

        property.removeRental(rentalAgreementBack);
        assertThat(property.getRentals()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getProperty()).isNull();

        property.rentals(new HashSet<>(Set.of(rentalAgreementBack)));
        assertThat(property.getRentals()).containsOnly(rentalAgreementBack);
        assertThat(rentalAgreementBack.getProperty()).isEqualTo(property);

        property.setRentals(new HashSet<>());
        assertThat(property.getRentals()).doesNotContain(rentalAgreementBack);
        assertThat(rentalAgreementBack.getProperty()).isNull();
    }

    @Test
    void houseCharacteristicTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        HouseCharacteristics houseCharacteristicsBack = getHouseCharacteristicsRandomSampleGenerator();

        property.addHouseCharacteristic(houseCharacteristicsBack);
        assertThat(property.getHouseCharacteristics()).containsOnly(houseCharacteristicsBack);
        assertThat(houseCharacteristicsBack.getProperty()).isEqualTo(property);

        property.removeHouseCharacteristic(houseCharacteristicsBack);
        assertThat(property.getHouseCharacteristics()).doesNotContain(houseCharacteristicsBack);
        assertThat(houseCharacteristicsBack.getProperty()).isNull();

        property.houseCharacteristics(new HashSet<>(Set.of(houseCharacteristicsBack)));
        assertThat(property.getHouseCharacteristics()).containsOnly(houseCharacteristicsBack);
        assertThat(houseCharacteristicsBack.getProperty()).isEqualTo(property);

        property.setHouseCharacteristics(new HashSet<>());
        assertThat(property.getHouseCharacteristics()).doesNotContain(houseCharacteristicsBack);
        assertThat(houseCharacteristicsBack.getProperty()).isNull();
    }

    @Test
    void reviewsTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        property.addReviews(reviewBack);
        assertThat(property.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProperty()).isEqualTo(property);

        property.removeReviews(reviewBack);
        assertThat(property.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProperty()).isNull();

        property.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(property.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getProperty()).isEqualTo(property);

        property.setReviews(new HashSet<>());
        assertThat(property.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getProperty()).isNull();
    }

    @Test
    void propertysPhotoTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        property.addPropertysPhoto(imageBack);
        assertThat(property.getPropertysPhotos()).containsOnly(imageBack);
        assertThat(imageBack.getProperty()).isEqualTo(property);

        property.removePropertysPhoto(imageBack);
        assertThat(property.getPropertysPhotos()).doesNotContain(imageBack);
        assertThat(imageBack.getProperty()).isNull();

        property.propertysPhotos(new HashSet<>(Set.of(imageBack)));
        assertThat(property.getPropertysPhotos()).containsOnly(imageBack);
        assertThat(imageBack.getProperty()).isEqualTo(property);

        property.setPropertysPhotos(new HashSet<>());
        assertThat(property.getPropertysPhotos()).doesNotContain(imageBack);
        assertThat(imageBack.getProperty()).isNull();
    }

    @Test
    void landLordTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        LandLord landLordBack = getLandLordRandomSampleGenerator();

        property.setLandLord(landLordBack);
        assertThat(property.getLandLord()).isEqualTo(landLordBack);

        property.landLord(null);
        assertThat(property.getLandLord()).isNull();
    }

    @Test
    void tenantPropertyPreferencesTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        TenantPropertyPreferences tenantPropertyPreferencesBack = getTenantPropertyPreferencesRandomSampleGenerator();

        property.setTenantPropertyPreferences(tenantPropertyPreferencesBack);
        assertThat(property.getTenantPropertyPreferences()).isEqualTo(tenantPropertyPreferencesBack);
        assertThat(tenantPropertyPreferencesBack.getProperty()).isEqualTo(property);

        property.tenantPropertyPreferences(null);
        assertThat(property.getTenantPropertyPreferences()).isNull();
        assertThat(tenantPropertyPreferencesBack.getProperty()).isNull();
    }
}
