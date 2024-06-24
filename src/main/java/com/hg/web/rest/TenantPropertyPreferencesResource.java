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

    //    /**
    //     * {@code POST  /tenant-property-preferences} : Create a new tenantPropertyPreferences.
    //     *
    //     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to create.
    //     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenantPropertyPreferencesDTO, or with status {@code 400 (Bad Request)} if the tenantPropertyPreferences has already an ID.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PostMapping("")
    //    public ResponseEntity<TenantPropertyPreferencesDTO> createTenantPropertyPreferences(
    //        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to save TenantPropertyPreferences : {}", tenantPropertyPreferencesDTO);
    //        if (tenantPropertyPreferencesDTO.getId() != null) {
    //            throw new BaseException("A new tenantPropertyPreferences cannot already have an ID", ENTITY_NAME, "idexists");
    //        }
    //        tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.save(tenantPropertyPreferencesDTO);
    //        return ResponseEntity.created(new URI("/api/tenant-property-preferences/" + tenantPropertyPreferencesDTO.getId()))
    //            .headers(
    //                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
    //            )
    //            .body(tenantPropertyPreferencesDTO);
    //    }
    //
    //    /**
    //     * {@code PUT  /tenant-property-preferences/:id} : Updates an existing tenantPropertyPreferences.
    //     *
    //     * @param id the id of the tenantPropertyPreferencesDTO to save.
    //     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantPropertyPreferencesDTO,
    //     * or with status {@code 400 (Bad Request)} if the tenantPropertyPreferencesDTO is not valid,
    //     * or with status {@code 500 (Internal Server Error)} if the tenantPropertyPreferencesDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PutMapping("/{id}")
    //    public ResponseEntity<TenantPropertyPreferencesDTO> updateTenantPropertyPreferences(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update TenantPropertyPreferences : {}, {}", id, tenantPropertyPreferencesDTO);
    //        if (tenantPropertyPreferencesDTO.getId() == null) {
    //            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, tenantPropertyPreferencesDTO.getId())) {
    //            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!tenantPropertyPreferencesRepository.existsById(id)) {
    //            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.update(tenantPropertyPreferencesDTO);
    //        return ResponseEntity.ok()
    //            .headers(
    //                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
    //            )
    //            .body(tenantPropertyPreferencesDTO);
    //    }
    //
    //    /**
    //     * {@code PATCH  /tenant-property-preferences/:id} : Partial updates given fields of an existing tenantPropertyPreferences, field will ignore if it is null
    //     *
    //     * @param id the id of the tenantPropertyPreferencesDTO to save.
    //     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantPropertyPreferencesDTO,
    //     * or with status {@code 400 (Bad Request)} if the tenantPropertyPreferencesDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the tenantPropertyPreferencesDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the tenantPropertyPreferencesDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<TenantPropertyPreferencesDTO> partialUpdateTenantPropertyPreferences(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update TenantPropertyPreferences partially : {}, {}", id, tenantPropertyPreferencesDTO);
    //        if (tenantPropertyPreferencesDTO.getId() == null) {
    //            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, tenantPropertyPreferencesDTO.getId())) {
    //            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!tenantPropertyPreferencesRepository.existsById(id)) {
    //            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<TenantPropertyPreferencesDTO> result = tenantPropertyPreferencesService.partialUpdate(tenantPropertyPreferencesDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
    //        );
    //    }
    //
    //    /**
    //     * {@code GET  /tenant-property-preferences} : get all the tenantPropertyPreferences.
    //     *
    //     * @param pageable the pagination information.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenantPropertyPreferences in body.
    //     */
    //    @GetMapping("")
    //    public ResponseEntity<List<TenantPropertyPreferencesDTO>> getAllTenantPropertyPreferences(
    //        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    //    ) {
    //        log.debug("REST request to get a page of TenantPropertyPreferences");
    //        Page<TenantPropertyPreferencesDTO> page = tenantPropertyPreferencesService.findAll(pageable);
    //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    //        return ResponseEntity.ok().headers(headers).body(page.getContent());
    //    }
    //
    //    /**
    //     * {@code GET  /tenant-property-preferences/:id} : get the "id" tenantPropertyPreferences.
    //     *
    //     * @param id the id of the tenantPropertyPreferencesDTO to retrieve.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenantPropertyPreferencesDTO, or with status {@code 404 (Not Found)}.
    //     */
    //    @GetMapping("/{id}")
    //    public ResponseEntity<TenantPropertyPreferencesDTO> getTenantPropertyPreferences(@PathVariable("id") Long id) {
    //        log.debug("REST request to get TenantPropertyPreferences : {}", id);
    //        Optional<TenantPropertyPreferencesDTO> tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.findOne(id);
    //        return ResponseUtil.wrapOrNotFound(tenantPropertyPreferencesDTO);
    //    }
    //
    //    /**
    //     * {@code DELETE  /tenant-property-preferences/:id} : delete the "id" tenantPropertyPreferences.
    //     *
    //     * @param id the id of the tenantPropertyPreferencesDTO to delete.
    //     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //     */
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteTenantPropertyPreferences(@PathVariable("id") Long id) {
    //        log.debug("REST request to delete TenantPropertyPreferences : {}", id);
    //        tenantPropertyPreferencesService.delete(id);
    //        return ResponseEntity.noContent()
    //            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
    //            .build();
    //    }

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
