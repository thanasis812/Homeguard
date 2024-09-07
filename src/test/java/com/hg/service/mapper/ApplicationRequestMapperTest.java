package com.hg.service.mapper;

import static com.hg.domain.ApplicationRequestAsserts.*;
import static com.hg.domain.ApplicationRequestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationRequestMapperTest {

    private ApplicationRequestMapper applicationRequestMapper;

    @BeforeEach
    void setUp() {
        applicationRequestMapper = new ApplicationRequestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApplicationRequestSample1();
        var actual = applicationRequestMapper.toEntity(applicationRequestMapper.toDto(expected));
        assertApplicationRequestAllPropertiesEquals(expected, actual);
    }
}
