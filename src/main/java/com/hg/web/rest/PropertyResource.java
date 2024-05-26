package com.hg.web.rest;

import com.hg.repository.PropertyRepository;
import com.hg.service.PropertyQueryService;
import com.hg.service.PropertyService;
import com.hg.service.criteria.PropertyCriteria;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.NewHouseRequestDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
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
 * REST controller for managing {@link com.hg.domain.Property}.
 */
@RestController
@RequestMapping("/api/properties")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private static final String ENTITY_NAME = "property";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropertyService propertyService;

    private final PropertyRepository propertyRepository;

    private final PropertyQueryService propertyQueryService;

    public PropertyResource(
        PropertyService propertyService,
        PropertyRepository propertyRepository,
        PropertyQueryService propertyQueryService
    ) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
        this.propertyQueryService = propertyQueryService;
    }

    /**
     * THIS IS THE DIRECT SAVE THE OLD ONE
     * {@code POST  /properties/crud} : Create a new property with db schema.
     *
     * @param propertyDTO the propertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propertyDTO, or with status {@code 400 (Bad Request)} if the property has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("OLD")
    public ResponseEntity<PropertyDTO> createPropertyCrud(@Valid @RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);
        if (propertyDTO.getId() != null) {
            throw new BaseException("A new property cannot already have an ID", ENTITY_NAME, "idexists");
        }
        propertyDTO = propertyService.save(propertyDTO);
        return ResponseEntity.created(new URI("/api/properties/" + propertyDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, propertyDTO.getId().toString()))
            .body(propertyDTO);
    }

    /**
     * {@code POST  /properties} : Create a new property.
     *
     * @param newHouseRequestDTO the propertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propertyDTO, or with status {@code 400 (Bad Request)} if the property has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NewHouseRequestDTO> createProperty(@Valid @RequestBody NewHouseRequestDTO newHouseRequestDTO)
        throws URISyntaxException {
        log.debug("REST request to save Property : {}", newHouseRequestDTO.toString());
        if (newHouseRequestDTO.getId() != null) {
            throw new BaseException("A new property cannot already have an ID", ENTITY_NAME, "idexists");
        }
        newHouseRequestDTO = propertyService.save(newHouseRequestDTO);
        return ResponseEntity.created(new URI("/api/properties/" + newHouseRequestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, newHouseRequestDTO.getId().toString()))
            .body(newHouseRequestDTO);
    }

    /**
     * {@code PUT  /properties/:id} : Updates an existing property.
     *
     * @param id the id of the propertyDTO to save.
     * @param propertyDTO the propertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propertyDTO,
     * or with status {@code 400 (Bad Request)} if the propertyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PropertyDTO propertyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Property : {}, {}", id, propertyDTO);
        if (propertyDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, propertyDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propertyRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        propertyDTO = propertyService.update(propertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propertyDTO.getId().toString()))
            .body(propertyDTO);
    }

    /**
     * {@code PATCH  /properties/:id} : Partial updates given fields of an existing property, field will ignore if it is null
     *
     * @param id the id of the propertyDTO to save.
     * @param propertyDTO the propertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propertyDTO,
     * or with status {@code 400 (Bad Request)} if the propertyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the propertyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the propertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PropertyDTO> partialUpdateProperty(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PropertyDTO propertyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Property partially : {}, {}", id, propertyDTO);
        if (propertyDTO.getId() == null) {
            throw new BaseException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, propertyDTO.getId())) {
            throw new BaseException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!propertyRepository.existsById(id)) {
            throw new BaseException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PropertyDTO> result = propertyService.partialUpdate(propertyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propertyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /properties} : get all the properties by criteria
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of properties in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PropertyDossierDTO>> getAllProperties(
        PropertyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Properties by criteria: {}", criteria);

        Page<PropertyDossierDTO> page = propertyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /properties/count} : count all the properties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProperties(PropertyCriteria criteria) {
        log.debug("REST request to count Properties by criteria: {}", criteria);
        return ResponseEntity.ok().body(propertyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /properties/crud/:id} : get the "id" property.
     * This differs from /properties/:id with fetching the vanilla object
     *
     * @param id the id of the propertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("crud/{id}")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable("id") Long id) {
        log.debug("REST request to get Property : {}", id);
        Optional<PropertyDTO> propertyDTO = propertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }

    /**
     * {@code DELETE  /properties/:id} : delete the "id" property.
     *
     * @param id the id of the propertyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable("id") Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * This might be used in the future
     */

    /**
     * {@code GET  /properties/search} : get all the properties by criteria
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of properties in body.
     */
    @GetMapping("search")
    public ResponseEntity<List<PropertyDossierDTO>> searchProperties(
        PropertyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Properties by criteria: {}", criteria);

        Page<PropertyDossierDTO> page = propertyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /properties/:id} : get the "id" property.
     * environment.endpoints.houses.houseDetails + "/" + ":id",
     * @param id the id of the propertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDossierDTO> getPropertyById(@PathVariable("id") Long id) {
        log.debug("REST request to get Property : {}", id);
        Optional<PropertyDossierDTO> propertyDTO = propertyService.getPropertyById(id);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }

    /**
     * {@code GET  /properties/landlord/:landlordId} : get all the property's of landlord
     *
     * @param landlordId the id of the propertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<PropertyDossierDTO>> getUsersHouses(@PathVariable("landlordId") Long landlordId) {
        log.debug("REST request to get Propertys for Land Lord id : {}", landlordId);
        List<PropertyDossierDTO> propertyDTO = propertyService.getUserProperties(landlordId);
        return ResponseUtil.wrapOrNotFound(Optional.of(propertyDTO));
    }

    /**
     * {@code GET  /properties/:propertyId/landlord/:landlordId} : get if landlord is certified
     * @param propertyId the property ID whom to fetch for
     * @param landlordId the landlord ID  whom to fetch for
     * @return Boolean value if property and landlord is certified
     */
    @GetMapping("{propertyId}/landlord/{landlordId}")
    public ResponseEntity<Boolean> checkLandlordId(
        @PathVariable("propertyId") Long propertyId,
        @PathVariable("landlordId") Long landlordId
    ) {
        log.debug("REST request to get check land lord for property {} and for landlord id  : {}", propertyId, landlordId);
        Boolean propertyDTO = propertyService.isCertified(propertyId, landlordId);
        return ResponseUtil.wrapOrNotFound(Optional.of(propertyDTO));
    }
}
