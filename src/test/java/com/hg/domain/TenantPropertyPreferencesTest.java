package com.hg.domain;

import static com.hg.domain.PropertyTestSamples.*;
import static com.hg.domain.TenantPropertyPreferencesTestSamples.*;
import static com.hg.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TenantPropertyPreferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPropertyPreferences.class);
        TenantPropertyPreferences tenantPropertyPreferences1 = getTenantPropertyPreferencesSample1();
        TenantPropertyPreferences tenantPropertyPreferences2 = new TenantPropertyPreferences();
        assertThat(tenantPropertyPreferences1).isNotEqualTo(tenantPropertyPreferences2);

        tenantPropertyPreferences2.setId(tenantPropertyPreferences1.getId());
        assertThat(tenantPropertyPreferences1).isEqualTo(tenantPropertyPreferences2);

        tenantPropertyPreferences2 = getTenantPropertyPreferencesSample2();
        assertThat(tenantPropertyPreferences1).isNotEqualTo(tenantPropertyPreferences2);
    }

    @Test
    void propertyTest() throws Exception {
        TenantPropertyPreferences tenantPropertyPreferences = getTenantPropertyPreferencesRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        tenantPropertyPreferences.setProperty(propertyBack);
        assertThat(tenantPropertyPreferences.getProperty()).isEqualTo(propertyBack);

        tenantPropertyPreferences.property(null);
        assertThat(tenantPropertyPreferences.getProperty()).isNull();
    }

    @Test
    void tenantTest() throws Exception {
        TenantPropertyPreferences tenantPropertyPreferences = getTenantPropertyPreferencesRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        tenantPropertyPreferences.setTenant(tenantBack);
        assertThat(tenantPropertyPreferences.getTenant()).isEqualTo(tenantBack);

        tenantPropertyPreferences.tenant(null);
        assertThat(tenantPropertyPreferences.getTenant()).isNull();
    }
}
