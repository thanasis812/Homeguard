package com.hg.domain;

import static com.hg.domain.LocationTestSamples.*;
import static com.hg.domain.PropertyTestSamples.*;
import static com.hg.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void tenantTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        location.setTenant(tenantBack);
        assertThat(location.getTenant()).isEqualTo(tenantBack);
        assertThat(tenantBack.getLocation()).isEqualTo(location);

        location.tenant(null);
        assertThat(location.getTenant()).isNull();
        assertThat(tenantBack.getLocation()).isNull();
    }

    @Test
    void propertyTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        location.setProperty(propertyBack);
        assertThat(location.getProperty()).isEqualTo(propertyBack);
        assertThat(propertyBack.getLocation()).isEqualTo(location);

        location.property(null);
        assertThat(location.getProperty()).isNull();
        assertThat(propertyBack.getLocation()).isNull();
    }
}
