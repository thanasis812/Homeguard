package com.hg.web.rest;

import com.hg.repository.RentalAgreementRepository;
import com.hg.service.RentalAgreementService;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.dto.mydto.RentalApplicationStatusDTO;
import com.hg.web.rest.errors.BaseException;
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
 * REST controller for managing {@link com.hg.domain.RentalAgreement}.
 */
@RestController
@RequestMapping("/api/rental-agreements")
public class RentalAgreementResource {

    private final Logger log = LoggerFactory.getLogger(RentalAgreementResource.class);

    private static final String ENTITY_NAME = "rentalAgreement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentalAgreementService rentalAgreementService;

    private final RentalAgreementRepository rentalAgreementRepository;

    public RentalAgreementResource(RentalAgreementService rentalAgreementService, RentalAgreementRepository rentalAgreementRepository) {
        this.rentalAgreementService = rentalAgreementService;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    /**
     * {@code POST  /rental-agreements} : Create a new rentalAgreement.
     *
     * @param rentalAgreementDTO the rentalAgreementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentalAgreementDTO, or with status {@code 400 (Bad Request)} if the rentalAgreement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RentalAgreementDTO> createRentalAgreement(@Valid @RequestBody RentalAgreementDTO rentalAgreementDTO)
        throws URISyntaxException {
        log.debug("REST request to save RentalAgreement : {}", rentalAgreementDTO);
        if (rentalAgreementDTO.getId() != null) {
            throw new BaseException("A new rentalAgreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rentalAgreementDTO = rentalAgreementService.save(rentalAgreementDTO);
        return ResponseEntity.created(new URI("/api/rental-agreements/" + rentalAgreementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rentalAgreementDTO.getId().toString()))
            .body(rentalAgreementDTO);
    }

    /**
     * {@code PUT  /rental-agreements/:id} : Updates an existing rentalAgreement.
     *
     * @param id the id of the rentalAgreementDTO to save.
     * @param rentalAgreementDTO the rentalAgreementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentalAgreementDTO,
     * or with status {@code 400 (Bad Request)} if the rentalAgreementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentalAgreementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RentalAgreementDTO> updateRentalAgreement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RentalAgreementDTO rentalAgreementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RentalAgreement : {}, {}", id, rentalAgreementDTO);
        if (rentalAgreementDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentalAgreementDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentalAgreementRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rentalAgreementDTO = rentalAgreementService.update(rentalAgreementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rentalAgreementDTO.getId().toString()))
            .body(rentalAgreementDTO);
    }

    /**
     * {@code PATCH  /rental-agreements/:id} : Partial updates given fields of an existing rentalAgreement, field will ignore if it is null
     *
     * @param id the id of the rentalAgreementDTO to save.
     * @param rentalAgreementDTO the rentalAgreementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentalAgreementDTO,
     * or with status {@code 400 (Bad Request)} if the rentalAgreementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rentalAgreementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rentalAgreementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RentalAgreementDTO> partialUpdateRentalAgreement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RentalAgreementDTO rentalAgreementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RentalAgreement partially : {}, {}", id, rentalAgreementDTO);
        if (rentalAgreementDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentalAgreementDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentalAgreementRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RentalAgreementDTO> result = rentalAgreementService.partialUpdate(rentalAgreementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rentalAgreementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rental-agreements} : get all the rentalAgreements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentalAgreements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RentalAgreementDTO>> getAllRentalAgreements(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RentalAgreements");
        Page<RentalAgreementDTO> page = rentalAgreementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /rental-agreements/:id} : delete the "id" rentalAgreement.
     *
     * @param id the id of the rentalAgreementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalAgreement(@PathVariable("id") Long id) {
        log.debug("REST request to delete RentalAgreement : {}", id);
        rentalAgreementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /rental-agreements/:tenantId} : get the rentalAgreement for selected tenant.
     *
     * @param tenantId the id of the rentalAgreementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentalAgreementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{tenantId}")
    public ResponseEntity<RentalAgreementDTO> getRentalAgreementByTenantId(
        @PathVariable(value = "tenantId", required = false) final Long tenantId
    ) throws URISyntaxException {
        log.debug("REST request to get RentalAgreement by tenant id : {}", tenantId);
        Optional<RentalAgreementDTO> rentalAgreementDTO = rentalAgreementService.findLatestActiveByTenant(tenantId);
        return ResponseUtil.wrapOrNotFound(rentalAgreementDTO);
    }

    /**
     * {@code GET  /rental-agreements/checkRentApplication/{tenantId}/property/{propertyId} : get rental agreement status for selected tenant and property
     *
     * @param tenantId the id of the tenant id to retrieve.
     * @param propertyId the id of the property to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentalAgreementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/checkRentApplication/{tenantId}/property/{propertyId}")
    public ResponseEntity<RentalApplicationStatusDTO> getRentalAgreementByTenantId(
        @PathVariable(value = "tenantId") final Long tenantId,
        @PathVariable(value = "propertyId") final Long propertyId
    ) {
        log.debug("REST request to get checkRentApplication  by tenant id : {} and property id {}", tenantId, propertyId);
        Optional<RentalApplicationStatusDTO> rentalAgreementDTO = rentalAgreementService.checkRentApplication(tenantId, propertyId);
        return ResponseUtil.wrapOrNotFound(rentalAgreementDTO);
    }

    /**
     * {@code GET  /rental-agreements/privateAgreementsTerms/{propertyId} : get private agreement terms for selected property
     * @param tenantId the id of the tenant id to retrieve.
     * @param propertyId the id of the property to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentalAgreementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/privateAgreementsTerms/{propertyId}")
    public ResponseEntity<String> getPrivateAgreementsTerms(@PathVariable(value = "propertyId") final Long propertyId) {
        log.debug("REST request to get getPrivateAgreementsTerms property id {}", propertyId);
        String rentalAgreement = rentalAgreementService.getPrivateAgreementsTerms(propertyId);
        return ResponseEntity.ok(rentalAgreement);
    }

    /**
     * {@code GET  /properties/tenant/:tenantId} : get the "tenantId" property.
     *  environment.endpoints.houses.userHouses + "/" + ":id",
     * @param tenantId the id of the propertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("property/tenant/{tenantId}")
    public ResponseEntity<PropertyDossierDTO> getPropertyByTenantId(@PathVariable("tenantId") Long tenantId) {
        log.debug("REST request to get Property for tenantId : {}", tenantId);
        Optional<PropertyDossierDTO> propertyDTO = rentalAgreementService.getPropertyByTenantId(tenantId);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }
}
