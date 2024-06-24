package com.hg.web.rest;

import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.service.HouseCharacteristicsService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    /**
     * Retrieves the HouseCharacteristics for a specific property and group.
     *
     * @param id    the id of the property
     * @param group the group of characteristics to retrieve
     * @return a ResponseEntity containing a list of HouseCharacteristicsDTOs for the specified property and group
     */
    @GetMapping("houseChar/{id}")
    public ResponseEntity<List<HouseCharacteristicsDTO>> getHouseCharacteristicsByGroupAndProperty(
        @PathVariable("id") Long id,
        HouseCharacteristicsGroupEnum group
    ) {
        log.debug("REST request to get HouseCharacteristics for property : {} and group {}", id, group.toString());
        List<HouseCharacteristicsDTO> houseCharacteristicsDTO = houseCharacteristicsService.findByCharacteristicsByGroupAndProperty(
            id,
            group
        );
        return ResponseEntity.ok(houseCharacteristicsDTO);
    }
}
