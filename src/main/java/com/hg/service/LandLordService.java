package com.hg.service;

import com.hg.service.dto.LandLordDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.LandLord}.
 */
public interface LandLordService {
    /**
     * Save a landLord.
     *
     * @param landLordDTO the entity to save.
     * @return the persisted entity.
     */
    LandLordDTO save(LandLordDTO landLordDTO);

    /**
     * Updates a landLord.
     *
     * @param landLordDTO the entity to update.
     * @return the persisted entity.
     */
    LandLordDTO update(LandLordDTO landLordDTO);

    /**
     * Partially updates a landLord.
     *
     * @param landLordDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LandLordDTO> partialUpdate(LandLordDTO landLordDTO);

    /**
     * Get all the landLords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LandLordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" landLord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LandLordDTO> findOne(Long id);

    /**
     * Delete the "id" landLord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
