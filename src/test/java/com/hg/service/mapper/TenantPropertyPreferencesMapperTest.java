package com.hg.service.mapper;

import static com.hg.domain.TenantPropertyPreferencesAsserts.*;
import static com.hg.domain.TenantPropertyPreferencesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantPropertyPreferencesMapperTest {

    private TenantPropertyPreferencesMapper tenantPropertyPreferencesMapper;

    @BeforeEach
    void setUp() {
        tenantPropertyPreferencesMapper = new TenantPropertyPreferencesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTenantPropertyPreferencesSample1();
        var actual = tenantPropertyPreferencesMapper.toEntity(tenantPropertyPreferencesMapper.toDto(expected));
        assertTenantPropertyPreferencesAllPropertiesEquals(expected, actual);
    }
}
