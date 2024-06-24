package com.hg.web.rest;

import com.hg.repository.TenantPropertyPreferencesRepository;
import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.FavoritePropertyDTO;
import com.hg.service.dto.mydto.HousePropertyDTO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.hg.domain.TenantPropertyPreferences}.
 */
@Hidden
@RestController
@RequestMapping("/api/property-preferences")
public class TenantPropertyPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(TenantPropertyPreferencesResource.class);

    private static final String ENTITY_NAME = "tenantPropertyPreferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantPropertyPreferencesService tenantPropertyPreferencesService;

    private final TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository;
    private final HGTokenService tokenService;

    public TenantPropertyPreferencesResource(
        TenantPropertyPreferencesService tenantPropertyPreferencesService,
        TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository,
        HGTokenService tokenService
    ) {
        this.tenantPropertyPreferencesService = tenantPropertyPreferencesService;
        this.tenantPropertyPreferencesRepository = tenantPropertyPreferencesRepository;
        this.tokenService = tokenService;
    }

    /**
     * GET  /favourite-house : get the favourite and alarm houses for a given tenant.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favourite and alarm houses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favourite-house")
    public ResponseEntity<FavoritePropertyDTO> getFavouriteAndAlarmHouses(HttpServletRequest request) {
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        log.debug("REST request to getFavouriteAndAlarmHouses");
        FavoritePropertyDTO content = tenantPropertyPreferencesService.getFavouriteAndAlarmHouses(tenantId);
        return ResponseEntity.ok().body(content);
    }

    /**
     * POST  /favourite-house : Favorite a house for a given tenant.
     *
     * @param housePropertyDTO the house to favorite for the tenant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favorite house, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/favourite-house")
    public ResponseEntity<TenantPropertyPreferencesDTO> favoriteHouse(@RequestBody HousePropertyDTO housePropertyDTO) {
        log.debug("REST request to getFavouriteAndAlarmHouses");
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.favoritePropertyForTenant(
            housePropertyDTO.getHouseId(),
            housePropertyDTO.getTenantId(),
            housePropertyDTO.isFavorite()
        );
        return ResponseEntity.ok().body(content);
    }

    /**
     * POST  /alarm-house : Alarm a house for a given tenant.
     *
     * @param housePropertyDTO the house to alarm for the tenant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarm house, or with status {@code 404 (Not Found)}.
     *
     * @see #favoriteHouse(HousePropertyDTO)
     */
    @PostMapping("/alarm-house")
    public ResponseEntity<TenantPropertyPreferencesDTO> alarmHouse(@RequestBody HousePropertyDTO housePropertyDTO) {
        log.debug("REST request to getFavouriteAndAlarmHouses");
        TenantPropertyPreferencesDTO content = tenantPropertyPreferencesService.alarmPropertyForTenant(
            housePropertyDTO.getHouseId(),
            housePropertyDTO.getTenantId(),
            housePropertyDTO.isReminder()
        );
        return ResponseEntity.ok().body(content);
    }
}
