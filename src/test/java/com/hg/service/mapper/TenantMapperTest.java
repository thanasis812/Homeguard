package com.hg.service.mapper;

import static com.hg.domain.TenantAsserts.*;
import static com.hg.domain.TenantTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantMapperTest {

    private TenantMapper tenantMapper;

    @BeforeEach
    void setUp() {
        tenantMapper = new TenantMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTenantSample1();
        var actual = tenantMapper.toEntity(tenantMapper.toDto(expected));
        assertTenantAllPropertiesEquals(expected, actual);
    }
}
