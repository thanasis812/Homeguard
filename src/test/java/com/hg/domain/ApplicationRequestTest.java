package com.hg.domain;

import static com.hg.domain.ApplicationRequestTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationRequest.class);
        ApplicationRequest applicationRequest1 = getApplicationRequestSample1();
        ApplicationRequest applicationRequest2 = new ApplicationRequest();
        assertThat(applicationRequest1).isNotEqualTo(applicationRequest2);

        applicationRequest2.setId(applicationRequest1.getId());
        assertThat(applicationRequest1).isEqualTo(applicationRequest2);

        applicationRequest2 = getApplicationRequestSample2();
        assertThat(applicationRequest1).isNotEqualTo(applicationRequest2);
    }

    @Test
    void propertyTest() throws Exception {
        ApplicationRequest applicationRequest = getApplicationRequestRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        applicationRequest.setProperty(propertyBack);
        assertThat(applicationRequest.getProperty()).isEqualTo(propertyBack);

        applicationRequest.property(null);
        assertThat(applicationRequest.getProperty()).isNull();
    }
}
