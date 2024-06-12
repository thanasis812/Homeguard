package com.hg.web.rest;

import com.hg.repository.LandLordRepository;
import com.hg.service.LandLordService;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.hg.domain.LandLord}.
 */
@Hidden
@RestController
@Deprecated
@RequestMapping("/api/land-lords")
public class LandLordResource {

    private final Logger log = LoggerFactory.getLogger(LandLordResource.class);

    private static final String ENTITY_NAME = "landLord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandLordService landLordService;

    private final LandLordRepository landLordRepository;

    public LandLordResource(LandLordService landLordService, LandLordRepository landLordRepository) {
        this.landLordService = landLordService;
        this.landLordRepository = landLordRepository;
    }
    //    /**
    //     * {@code POST  /land-lords} : Create a new landLord.
    //     *
    //     * @param landLordDTO the landLordDTO to create.
    //     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landLordDTO, or with status {@code 400 (Bad Request)} if the landLord has already an ID.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PostMapping("")
    //    public ResponseEntity<LandLordDTO> createLandLord(@Valid @RequestBody LandLordDTO landLordDTO) throws URISyntaxException {
    //        log.debug("REST request to save LandLord : {}", landLordDTO);
    //        if (landLordDTO.getId() != null) {
    //            throw new BaseException("A new landLord cannot already have an ID", ENTITY_NAME, "idexists");
    //        }
    //        landLordDTO = landLordService.save(landLordDTO);
    //        return ResponseEntity.created(new URI("/api/land-lords/" + landLordDTO.getId()))
    //            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, landLordDTO.getId().toString()))
    //            .body(landLordDTO);
    //    }
    //
    //    /**
    //     * {@code PUT  /land-lords/:id} : Updates an existing landLord.
    //     *
    //     * @param id the id of the landLordDTO to save.
    //     * @param landLordDTO the landLordDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landLordDTO,
    //     * or with status {@code 400 (Bad Request)} if the landLordDTO is not valid,
    //     * or with status {@code 500 (Internal Server Error)} if the landLordDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PutMapping("/{id}")
    //    public ResponseEntity<LandLordDTO> updateLandLord(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @Valid @RequestBody LandLordDTO landLordDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to update LandLord : {}, {}", id, landLordDTO);
    //        if (landLordDTO.getId() == null) {
    //            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, landLordDTO.getId())) {
    //            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!landLordRepository.existsById(id)) {
    //            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        landLordDTO = landLordService.update(landLordDTO);
    //        return ResponseEntity.ok()
    //            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landLordDTO.getId().toString()))
    //            .body(landLordDTO);
    //    }
    //
    //    /**
    //     * {@code PATCH  /land-lords/:id} : Partial updates given fields of an existing landLord, field will ignore if it is null
    //     *
    //     * @param id the id of the landLordDTO to save.
    //     * @param landLordDTO the landLordDTO to update.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landLordDTO,
    //     * or with status {@code 400 (Bad Request)} if the landLordDTO is not valid,
    //     * or with status {@code 404 (Not Found)} if the landLordDTO is not found,
    //     * or with status {@code 500 (Internal Server Error)} if the landLordDTO couldn't be updated.
    //     * @throws URISyntaxException if the Location URI syntax is incorrect.
    //     */
    //    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<LandLordDTO> partialUpdateLandLord(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @NotNull @RequestBody LandLordDTO landLordDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update LandLord partially : {}, {}", id, landLordDTO);
    //        if (landLordDTO.getId() == null) {
    //            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, landLordDTO.getId())) {
    //            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!landLordRepository.existsById(id)) {
    //            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<LandLordDTO> result = landLordService.partialUpdate(landLordDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, landLordDTO.getId().toString())
    //        );
    //    }
    //
    //    /**
    //     * {@code GET  /land-lords} : get all the landLords.
    //     *
    //     * @param pageable the pagination information.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landLords in body.
    //     */
    //    @GetMapping("")
    //    public ResponseEntity<List<LandLordDTO>> getAllLandLords(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
    //        log.debug("REST request to get a page of LandLords");
    //        Page<LandLordDTO> page = landLordService.findAll(pageable);
    //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    //        return ResponseEntity.ok().headers(headers).body(page.getContent());
    //    }

    //    /**
    //     * {@code GET  /land-lords/:id} : get the "id" landLord.
    //     *
    //     * @param id the id of the landLordDTO to retrieve.
    //     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landLordDTO, or with status {@code 404 (Not Found)}.
    //     */
    //    @GetMapping("/{id}")
    //    public ResponseEntity<LandLordDTO> getLandLord(@PathVariable("id") Long id) {
    //        log.debug("REST request to get LandLord : {}", id);
    //        Optional<LandLordDTO> landLordDTO = landLordService.findOne(id);
    //        return ResponseUtil.wrapOrNotFound(landLordDTO);
    //    }
    //
    //    /**
    //     * {@code DELETE  /land-lords/:id} : delete the "id" landLord.
    //     *
    //     * @param id the id of the landLordDTO to delete.
    //     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //     */
    //    @DeleteMapping("/{id}")
    //    public ResponseEntity<Void> deleteLandLord(@PathVariable("id") Long id) {
    //        log.debug("REST request to delete LandLord : {}", id);
    //        landLordService.delete(id);
    //        return ResponseEntity.noContent()
    //            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
    //            .build();
    //    }
}
