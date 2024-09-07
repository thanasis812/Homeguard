package com.hg.domain;

import static com.hg.domain.ImageTestSamples.*;
import static com.hg.domain.LandLordTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static com.hg.domain.ReviewTestSamples.*;
import static com.hg.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Image.class);
        Image image1 = getImageSample1();
        Image image2 = new Image();
        assertThat(image1).isNotEqualTo(image2);

        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);

        image2 = getImageSample2();
        assertThat(image1).isNotEqualTo(image2);
    }

    @Test
    void tenantTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        image.setTenant(tenantBack);
        assertThat(image.getTenant()).isEqualTo(tenantBack);
        assertThat(tenantBack.getTenantImage()).isEqualTo(image);

        image.tenant(null);
        assertThat(image.getTenant()).isNull();
        assertThat(tenantBack.getTenantImage()).isNull();
    }

    @Test
    void landLordTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        LandLord landLordBack = getLandLordRandomSampleGenerator();

        image.setLandLord(landLordBack);
        assertThat(image.getLandLord()).isEqualTo(landLordBack);
        assertThat(landLordBack.getLandLordImage()).isEqualTo(image);

        image.landLord(null);
        assertThat(image.getLandLord()).isNull();
        assertThat(landLordBack.getLandLordImage()).isNull();
    }

    @Test
    void propertyTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        image.setProperty(propertyBack);
        assertThat(image.getProperty()).isEqualTo(propertyBack);

        image.property(null);
        assertThat(image.getProperty()).isNull();
    }

    @Test
    void reviewTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        image.setReview(reviewBack);
        assertThat(image.getReview()).isEqualTo(reviewBack);

        image.review(null);
        assertThat(image.getReview()).isNull();
    }
}
