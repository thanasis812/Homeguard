package com.hg.web.rest;

import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.service.HouseCharacteristicsService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.web.rest.errors.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.hg.domain.HouseCharacteristics}.
 */
@Hidden
@RestController
@RequestMapping("/api/house-characteristics")
public class HouseCharacteristicsResource {

    private final Logger log = LoggerFactory.getLogger(HouseCharacteristicsResource.class);

    private static final String ENTITY_NAME = "houseCharacteristics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HouseCharacteristicsService houseCharacteristicsService;

    private final HouseCharacteristicsRepository houseCharacteristicsRepository;

    public HouseCharacteristicsResource(
        HouseCharacteristicsService houseCharacteristicsService,
        HouseCharacteristicsRepository houseCharacteristicsRepository
    ) {
        this.houseCharacteristicsService = houseCharacteristicsService;
        this.houseCharacteristicsRepository = houseCharacteristicsRepository;
    }

    /**
     * {@code POST  /house-characteristics} : Create a new houseCharacteristics.
     *
     * @param houseCharacteristicsDTO the houseCharacteristicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new houseCharacteristicsDTO, or with status {@code 400 (Bad Request)} if the houseCharacteristics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HouseCharacteristicsDTO> createHouseCharacteristics(
        @Valid @RequestBody HouseCharacteristicsDTO houseCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save HouseCharacteristics : {}", houseCharacteristicsDTO);
        if (houseCharacteristicsDTO.getId() != null) {
            throw new BaseException("A new houseCharacteristics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        houseCharacteristicsDTO = houseCharacteristicsService.save(houseCharacteristicsDTO);
        return ResponseEntity.created(new URI("/api/house-characteristics/" + houseCharacteristicsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, houseCharacteristicsDTO.getId().toString()))
            .body(houseCharacteristicsDTO);
    }

    /**
     * {@code PUT  /house-characteristics/:id} : Updates an existing houseCharacteristics.
     *
     * @param id the id of the houseCharacteristicsDTO to save.
     * @param houseCharacteristicsDTO the houseCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated houseCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the houseCharacteristicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the houseCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HouseCharacteristicsDTO> updateHouseCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HouseCharacteristicsDTO houseCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HouseCharacteristics : {}, {}", id, houseCharacteristicsDTO);
        if (houseCharacteristicsDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, houseCharacteristicsDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!houseCharacteristicsRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        houseCharacteristicsDTO = houseCharacteristicsService.update(houseCharacteristicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, houseCharacteristicsDTO.getId().toString()))
            .body(houseCharacteristicsDTO);
    }

    /**
     * {@code PATCH  /house-characteristics/:id} : Partial updates given fields of an existing houseCharacteristics, field will ignore if it is null
     *
     * @param id the id of the houseCharacteristicsDTO to save.
     * @param houseCharacteristicsDTO the houseCharacteristicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated houseCharacteristicsDTO,
     * or with status {@code 400 (Bad Request)} if the houseCharacteristicsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the houseCharacteristicsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the houseCharacteristicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HouseCharacteristicsDTO> partialUpdateHouseCharacteristics(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HouseCharacteristicsDTO houseCharacteristicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HouseCharacteristics partially : {}, {}", id, houseCharacteristicsDTO);
        if (houseCharacteristicsDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, houseCharacteristicsDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!houseCharacteristicsRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HouseCharacteristicsDTO> result = houseCharacteristicsService.partialUpdate(houseCharacteristicsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, houseCharacteristicsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /house-characteristics} : get all the houseCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of houseCharacteristics in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HouseCharacteristicsDTO>> getAllHouseCharacteristics(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of HouseCharacteristics");
        Page<HouseCharacteristicsDTO> page = houseCharacteristicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /house-characteristics/:id} : get the "id" houseCharacteristics.
     *
     * @param id the id of the houseCharacteristicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the houseCharacteristicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HouseCharacteristicsDTO> getHouseCharacteristics(@PathVariable("id") Long id) {
        log.debug("REST request to get HouseCharacteristics : {}", id);
        Optional<HouseCharacteristicsDTO> houseCharacteristicsDTO = houseCharacteristicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(houseCharacteristicsDTO);
    }

    /**
     * {@code DELETE  /house-characteristics/:id} : delete the "id" houseCharacteristics.
     *
     * @param id the id of the houseCharacteristicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouseCharacteristics(@PathVariable("id") Long id) {
        log.debug("REST request to delete HouseCharacteristics : {}", id);
        houseCharacteristicsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
