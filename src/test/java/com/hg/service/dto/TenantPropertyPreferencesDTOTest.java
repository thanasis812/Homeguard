package com.hg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TenantPropertyPreferencesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantPropertyPreferencesDTO.class);
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO1 = new TenantPropertyPreferencesDTO();
        tenantPropertyPreferencesDTO1.setId(1L);
        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO2 = new TenantPropertyPreferencesDTO();
        assertThat(tenantPropertyPreferencesDTO1).isNotEqualTo(tenantPropertyPreferencesDTO2);
        tenantPropertyPreferencesDTO2.setId(tenantPropertyPreferencesDTO1.getId());
        assertThat(tenantPropertyPreferencesDTO1).isEqualTo(tenantPropertyPreferencesDTO2);
        tenantPropertyPreferencesDTO2.setId(2L);
        assertThat(tenantPropertyPreferencesDTO1).isNotEqualTo(tenantPropertyPreferencesDTO2);
        tenantPropertyPreferencesDTO1.setId(null);
        assertThat(tenantPropertyPreferencesDTO1).isNotEqualTo(tenantPropertyPreferencesDTO2);
    }
}
