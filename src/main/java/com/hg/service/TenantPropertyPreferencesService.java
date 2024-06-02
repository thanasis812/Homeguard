package com.hg.service;

import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.FavoritePropertyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.TenantPropertyPreferences}.
 */
public interface TenantPropertyPreferencesService {
    /**
     * Save a tenantPropertyPreferences.
     *
     * @param tenantPropertyPreferencesDTO the entity to save.
     * @return the persisted entity.
     */
    TenantPropertyPreferencesDTO save(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO);

    /**
     * Updates a tenantPropertyPreferences.
     *
     * @param tenantPropertyPreferencesDTO the entity to update.
     * @return the persisted entity.
     */
    TenantPropertyPreferencesDTO update(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO);

    /**
     * Partially updates a tenantPropertyPreferences.
     *
     * @param tenantPropertyPreferencesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TenantPropertyPreferencesDTO> partialUpdate(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO);

    /**
     * Get all the tenantPropertyPreferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TenantPropertyPreferencesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tenantPropertyPreferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TenantPropertyPreferencesDTO> findOne(Long id);

    /**
     * Delete the "id" tenantPropertyPreferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    FavoritePropertyDTO getFavouriteAndAlarmHouses(Long tenantId);
}
