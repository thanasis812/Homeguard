package com.hg.web.rest;

import com.hg.service.PropertyQueryService;
import com.hg.service.PropertyService;
import com.hg.service.criteria.PropertyCriteria;
import com.hg.service.dto.mydto.NewHouseRequestDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.web.rest.errors.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    private final PropertyQueryService propertyQueryService;
    private final HGTokenService tokenService;

    public PropertyResource(PropertyService propertyService, PropertyQueryService propertyQueryService, HGTokenService tokenService) {
        this.propertyService = propertyService;
        this.propertyQueryService = propertyQueryService;
        this.tokenService = tokenService;
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
     * {@code GET  /properties/search} : get all the properties by criteria
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of properties in body.
     */
    @GetMapping("landlordHouses")
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
     * Retrieves a property by its ID.
     *
     * @param propertyId the ID of the property to retrieve
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the propertyDTO in body, or with status {@code 404 (Not Found)} if the property is not found
     */
    @GetMapping("houseDetails/{propertyId}")
    public ResponseEntity<PropertyDossierDTO> getSelectedHouse(@PathVariable("propertyId") Long propertyId) {
        log.debug("REST request to get property with id: {}", propertyId);

        Optional<PropertyDossierDTO> propertyDossierDTO = propertyService.findOneDto(propertyId);
        return ResponseUtil.wrapOrNotFound(propertyDossierDTO);
    }

    /**
     * {@code GET  /properties/} : get the "id" property.
     * environment.endpoints.houses.houseDetails + "/" + ":id",
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("tenantHouse")
    public ResponseEntity<PropertyDossierDTO> getPropertyByTenantId(HttpServletRequest request) {
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        log.debug("REST request to get Property : {}", tenantId);
        Optional<PropertyDossierDTO> propertyDTO = propertyService.getPropertyById(tenantId);
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
     *
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
