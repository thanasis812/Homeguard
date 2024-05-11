package com.hg.service;

import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.dto.mydto.RentalApplicationStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.RentalAgreement}.
 */
public interface RentalAgreementService {
    /**
     * Save a rentalAgreement.
     *
     * @param rentalAgreementDTO the entity to save.
     * @return the persisted entity.
     */
    RentalAgreementDTO save(RentalAgreementDTO rentalAgreementDTO);

    /**
     * Updates a rentalAgreement.
     *
     * @param rentalAgreementDTO the entity to update.
     * @return the persisted entity.
     */
    RentalAgreementDTO update(RentalAgreementDTO rentalAgreementDTO);

    /**
     * Partially updates a rentalAgreement.
     *
     * @param rentalAgreementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RentalAgreementDTO> partialUpdate(RentalAgreementDTO rentalAgreementDTO);

    /**
     * Get all the rentalAgreements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RentalAgreementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rentalAgreement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RentalAgreementDTO> findOne(Long id);

    /**
     * Delete the "id" rentalAgreement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<RentalAgreementDTO> findLatestActiveByTenant(Long tenantId);

    /**
     * Check the rental application status
     * @param tenantId for
     * @param propertyId for
     * @return RentalApplicationStatusDTO with the status of rental
     */
    Optional<RentalApplicationStatusDTO> checkRentApplication(Long tenantId, Long propertyId);

    /**
     * Get getPrivateAgreementsTerms for selected property
     *
     * @param propertyId the property id
     * @return the private agreement terms
     */
    String getPrivateAgreementsTerms(Long propertyId);

    /**
     * Get getPropertyById for selected tenantid
     *
     * @param tenantId the tenant id
     * @return the property he owns
     */
    Optional<PropertyDossierDTO> getPropertyByTenantId(Long tenantId);
}
