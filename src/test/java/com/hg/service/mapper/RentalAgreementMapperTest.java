package com.hg.service.mapper;

import static com.hg.domain.RentalAgreementAsserts.*;
import static com.hg.domain.RentalAgreementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalAgreementMapperTest {

    private RentalAgreementMapper rentalAgreementMapper;

    @BeforeEach
    void setUp() {
        rentalAgreementMapper = new RentalAgreementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentalAgreementSample1();
        var actual = rentalAgreementMapper.toEntity(rentalAgreementMapper.toDto(expected));
        assertRentalAgreementAllPropertiesEquals(expected, actual);
    }
}
