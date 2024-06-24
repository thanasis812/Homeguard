package com.hg.web.rest;

import com.hg.repository.RentalAgreementRepository;
import com.hg.service.RentalAgreementService;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.dto.mydto.RentalAgreementSaveDTO;
import com.hg.service.dto.mydto.RentalApplicationStatusDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hg.domain.RentalAgreement}.
 */
@RestController
@RequestMapping("/api/rental-agreements")
public class RentalAgreementResource {

    private final Logger log = LoggerFactory.getLogger(RentalAgreementResource.class);

    private final RentalAgreementService rentalAgreementService;

    private final HGTokenService tokenService;

    public RentalAgreementResource(RentalAgreementService rentalAgreementService, HGTokenService tokenService) {
        this.rentalAgreementService = rentalAgreementService;
        this.tokenService = tokenService;
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
     * @param tenantId   the id of the tenant id to retrieve.
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
     * @param tenantId   the id of the tenant id to retrieve.
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
     * environment.endpoints.houses.userHouses + "/" + ":id",
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("property/tenant}")
    public ResponseEntity<PropertyDossierDTO> getPropertyByTenantId(HttpServletRequest request) {
        Long tenantId = tokenService.getTenantId(request);
        if (tenantId == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no tenant ID found
        }
        log.debug("REST request to get Property for tenantId : {}", tenantId);

        Optional<PropertyDossierDTO> propertyDTO = rentalAgreementService.getPropertyByTenantId(tenantId);
        return ResponseUtil.wrapOrNotFound(propertyDTO);
    }

    /**
     * Saves the landlord id and property id.
     * environment.endpoints.houses.saveLandlordId
     * @param rentalAgreementSaveDTO the {@link com.hg.service.dto.mydto.RentalAgreementSaveDTO} containing the landlord id and property id to save.
     * @return the {@link ResponseEntity} with status {@code 200 (Created)} and with body the new.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/landLordId")
    public ResponseEntity<Void> saveLandlordId(@Valid @RequestBody RentalAgreementSaveDTO rentalAgreementSaveDTO)
        throws URISyntaxException {
        log.debug(
            "REST request to save LandLord id with ID: {} and property id {}",
            rentalAgreementSaveDTO.landLordId(),
            rentalAgreementSaveDTO.propertyId()
        );
        rentalAgreementService.saveLandLordId(rentalAgreementSaveDTO.landLordId(), rentalAgreementSaveDTO.propertyId());
        return ResponseEntity.ok().build();
    }
}
