package com.hg.service;

import com.hg.service.dto.ApplicationRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.ApplicationRequest}.
 */
public interface ApplicationRequestService {
    /**
     * Save a applicationRequest.
     *
     * @param applicationRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationRequestDTO save(ApplicationRequestDTO applicationRequestDTO);

    /**
     * Updates a applicationRequest.
     *
     * @param applicationRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ApplicationRequestDTO update(ApplicationRequestDTO applicationRequestDTO);

    /**
     * Partially updates a applicationRequest.
     *
     * @param applicationRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationRequestDTO> partialUpdate(ApplicationRequestDTO applicationRequestDTO);

    /**
     * Get all the applicationRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicationRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationRequestDTO> findOne(Long id);

    /**
     * Delete the "id" applicationRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
