package com.hg.web.rest;

import com.hg.repository.TenantPropertyPreferencesRepository;
import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.web.rest.errors.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.TenantPropertyPreferences}.
 */
@Hidden
@RestController
@RequestMapping("/api/tenant-property-preferences")
public class TenantPropertyPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(TenantPropertyPreferencesResource.class);

    private static final String ENTITY_NAME = "tenantPropertyPreferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantPropertyPreferencesService tenantPropertyPreferencesService;

    private final TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository;

    public TenantPropertyPreferencesResource(
        TenantPropertyPreferencesService tenantPropertyPreferencesService,
        TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository
    ) {
        this.tenantPropertyPreferencesService = tenantPropertyPreferencesService;
        this.tenantPropertyPreferencesRepository = tenantPropertyPreferencesRepository;
    }

    /**
     * {@code POST  /tenant-property-preferences} : Create a new tenantPropertyPreferences.
     *
     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenantPropertyPreferencesDTO, or with status {@code 400 (Bad Request)} if the tenantPropertyPreferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TenantPropertyPreferencesDTO> createTenantPropertyPreferences(
        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TenantPropertyPreferences : {}", tenantPropertyPreferencesDTO);
        if (tenantPropertyPreferencesDTO.getId() != null) {
            throw new BaseException("A new tenantPropertyPreferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.save(tenantPropertyPreferencesDTO);
        return ResponseEntity.created(new URI("/api/tenant-property-preferences/" + tenantPropertyPreferencesDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
            )
            .body(tenantPropertyPreferencesDTO);
    }

    /**
     * {@code PUT  /tenant-property-preferences/:id} : Updates an existing tenantPropertyPreferences.
     *
     * @param id the id of the tenantPropertyPreferencesDTO to save.
     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantPropertyPreferencesDTO,
     * or with status {@code 400 (Bad Request)} if the tenantPropertyPreferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenantPropertyPreferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TenantPropertyPreferencesDTO> updateTenantPropertyPreferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TenantPropertyPreferences : {}, {}", id, tenantPropertyPreferencesDTO);
        if (tenantPropertyPreferencesDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenantPropertyPreferencesDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenantPropertyPreferencesRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.update(tenantPropertyPreferencesDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
            )
            .body(tenantPropertyPreferencesDTO);
    }

    /**
     * {@code PATCH  /tenant-property-preferences/:id} : Partial updates given fields of an existing tenantPropertyPreferences, field will ignore if it is null
     *
     * @param id the id of the tenantPropertyPreferencesDTO to save.
     * @param tenantPropertyPreferencesDTO the tenantPropertyPreferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantPropertyPreferencesDTO,
     * or with status {@code 400 (Bad Request)} if the tenantPropertyPreferencesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tenantPropertyPreferencesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tenantPropertyPreferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TenantPropertyPreferencesDTO> partialUpdateTenantPropertyPreferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TenantPropertyPreferences partially : {}, {}", id, tenantPropertyPreferencesDTO);
        if (tenantPropertyPreferencesDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tenantPropertyPreferencesDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tenantPropertyPreferencesRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TenantPropertyPreferencesDTO> result = tenantPropertyPreferencesService.partialUpdate(tenantPropertyPreferencesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantPropertyPreferencesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tenant-property-preferences} : get all the tenantPropertyPreferences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenantPropertyPreferences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TenantPropertyPreferencesDTO>> getAllTenantPropertyPreferences(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TenantPropertyPreferences");
        Page<TenantPropertyPreferencesDTO> page = tenantPropertyPreferencesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tenant-property-preferences/:id} : get the "id" tenantPropertyPreferences.
     *
     * @param id the id of the tenantPropertyPreferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenantPropertyPreferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TenantPropertyPreferencesDTO> getTenantPropertyPreferences(@PathVariable("id") Long id) {
        log.debug("REST request to get TenantPropertyPreferences : {}", id);
        Optional<TenantPropertyPreferencesDTO> tenantPropertyPreferencesDTO = tenantPropertyPreferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tenantPropertyPreferencesDTO);
    }

    /**
     * {@code DELETE  /tenant-property-preferences/:id} : delete the "id" tenantPropertyPreferences.
     *
     * @param id the id of the tenantPropertyPreferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenantPropertyPreferences(@PathVariable("id") Long id) {
        log.debug("REST request to delete TenantPropertyPreferences : {}", id);
        tenantPropertyPreferencesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
