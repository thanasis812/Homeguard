package com.hg.service;

import com.hg.service.dto.ImageDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.Image}.
 */
public interface ImageService {
    /**
     * Save a image.
     *
     * @param imageDTO the entity to save.
     * @return the persisted entity.
     */
    ImageDTO save(ImageDTO imageDTO);

    /**
     * Updates a image.
     *
     * @param imageDTO the entity to update.
     * @return the persisted entity.
     */
    ImageDTO update(ImageDTO imageDTO);

    /**
     * Partially updates a image.
     *
     * @param imageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImageDTO> partialUpdate(ImageDTO imageDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageDTO> findAll(Pageable pageable);

    /**
     * Get all the ImageDTO where Tenant is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ImageDTO> findAllWhereTenantIsNull();
    /**
     * Get all the ImageDTO where LandLord is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ImageDTO> findAllWhereLandLordIsNull();

    /**
     * Get the "id" image.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageDTO> findOne(Long id);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
