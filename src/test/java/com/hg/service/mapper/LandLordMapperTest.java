package com.hg.service.mapper;

import static com.hg.domain.LandLordAsserts.assertLandLordAllPropertiesEquals;
import static com.hg.domain.LandLordTestSamples.getLandLordSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LandLordMapperTest {

    private LandLordMapper landLordMapper;

    @BeforeEach
    void setUp() {
        landLordMapper = new LandLordMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLandLordSample1();
        var actual = landLordMapper.toEntity(landLordMapper.toDto(expected));
        assertLandLordAllPropertiesEquals(expected, actual);
    }
}
