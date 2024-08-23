package com.hg.service.mapper;

import static com.hg.domain.HouseCharacteristicsAsserts.assertHouseCharacteristicsAllPropertiesEquals;
import static com.hg.domain.HouseCharacteristicsTestSamples.getHouseCharacteristicsSample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HouseCharacteristicsMapperTest {

    private HouseCharacteristicsMapper houseCharacteristicsMapper;

    @BeforeEach
    void setUp() {
        houseCharacteristicsMapper = new HouseCharacteristicsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHouseCharacteristicsSample1();
        var actual = houseCharacteristicsMapper.toEntity(houseCharacteristicsMapper.toDto(expected));
        assertHouseCharacteristicsAllPropertiesEquals(expected, actual);
    }
}
