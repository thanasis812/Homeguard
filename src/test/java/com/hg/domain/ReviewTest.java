package com.hg.domain;

import static com.hg.domain.ImageTestSamples.getImageRandomSampleGenerator;
import static com.hg.domain.LandLordTestSamples.getLandLordRandomSampleGenerator;
import static com.hg.domain.PropertyTestSamples.getPropertyRandomSampleGenerator;
import static com.hg.domain.ReviewTestSamples.*;
import static com.hg.domain.TenantTestSamples.getTenantRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void imagesTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        review.addImages(imageBack);
        assertThat(review.getImages()).containsOnly(imageBack);
        assertThat(imageBack.getReview()).isEqualTo(review);

        review.removeImages(imageBack);
        assertThat(review.getImages()).doesNotContain(imageBack);
        assertThat(imageBack.getReview()).isNull();

        review.images(new HashSet<>(Set.of(imageBack)));
        assertThat(review.getImages()).containsOnly(imageBack);
        assertThat(imageBack.getReview()).isEqualTo(review);

        review.setImages(new HashSet<>());
        assertThat(review.getImages()).doesNotContain(imageBack);
        assertThat(imageBack.getReview()).isNull();
    }

    @Test
    void tenantTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        review.setTenant(tenantBack);
        assertThat(review.getTenant()).isEqualTo(tenantBack);

        review.tenant(null);
        assertThat(review.getTenant()).isNull();
    }

    @Test
    void landLordTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        LandLord landLordBack = getLandLordRandomSampleGenerator();

        review.setLandLord(landLordBack);
        assertThat(review.getLandLord()).isEqualTo(landLordBack);

        review.landLord(null);
        assertThat(review.getLandLord()).isNull();
    }

    @Test
    void propertyTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        review.setProperty(propertyBack);
        assertThat(review.getProperty()).isEqualTo(propertyBack);

        review.property(null);
        assertThat(review.getProperty()).isNull();
    }
}
