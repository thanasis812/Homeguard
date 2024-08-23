package com.hg.web.rest;

import com.hg.service.HouseCharacteristicsService;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.hg.domain.HouseCharacteristics}.
 */
@Hidden
@RestController
@RequestMapping("/api/house-characteristics")
public class HouseCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(HouseCharacteristicsResource.class);
    private final HouseCharacteristicsService houseCharacteristicsService;

    public HouseCharacteristicsResource(HouseCharacteristicsService houseCharacteristicsService) {
        this.houseCharacteristicsService = houseCharacteristicsService;
    }
}
