package com.hg.service.mapper;

import static com.hg.domain.ImageAsserts.*;
import static com.hg.domain.ImageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageMapperTest {

    private ImageMapper imageMapper;

    @BeforeEach
    void setUp() {
        imageMapper = new ImageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getImageSample1();
        var actual = imageMapper.toEntity(imageMapper.toDto(expected));
        assertImageAllPropertiesEquals(expected, actual);
    }
}
