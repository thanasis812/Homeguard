package com.hg.web.rest;

import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.HouseCharacteristicsService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
