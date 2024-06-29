package com.hg.service;

import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.NewHouseRequestDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.hg.domain.Property}.
 */
public interface PropertyService {
    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save.
     * @return the persisted entity.
     */
    PropertyDTO save(PropertyDTO propertyDTO);

    /**
     * Updates a property.
     *
     * @param propertyDTO the entity to update.
     * @return the persisted entity.
     */
    PropertyDTO update(PropertyDTO propertyDTO);

    /**
     * Partially updates a property.
     *
     * @param propertyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PropertyDTO> partialUpdate(PropertyDTO propertyDTO);

    /**
     * Get all the PropertyDTO where TenantPropertyPreferences is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PropertyDTO> findAllWhereTenantPropertyPreferencesIsNull();

    /**
     * Get the "id" property.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropertyDTO> findOne(Long id);

    /**
     * Get the "id" property.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropertyDossierDTO> findOneDto(Long id);

    /**
     * Get the "id" of the property with the UI schema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropertyDossierDTO> getPropertyById(Long id);

    /**
     * Delete the "id" property.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * @param landlordId landlord id to fetches properties for
     * @return list of property's that belong to this landlord
     */
    List<PropertyDossierDTO> getUserProperties(Long landlordId);

    /**
     * Checks if a property is certified for a given landlord.
     *
     * @param propertyId the id of the property to check.
     * @param landlordId the id of the landlord to check for.
     * @return true if the property is certified for the landlord, false otherwise.
     */
    Boolean isCertified(Long propertyId, Long landlordId);

    /**
     * Saves a new house request to the database.
     * @param newHouseRequestDTO the new house request data to be saved.
     * @return the persisted new house request data.
     */
    NewHouseRequestDTO save(NewHouseRequestDTO newHouseRequestDTO);
}
