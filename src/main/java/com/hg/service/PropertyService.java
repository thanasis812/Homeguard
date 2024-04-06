package com.hg.service;

import com.hg.service.dto.PropertyDTO;
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
     * Delete the "id" property.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
