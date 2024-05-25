package com.hg.service;

import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hg.domain.HouseCharacteristics}.
 */
public interface HouseCharacteristicsService {
    /**
     * Save a houseCharacteristics.
     *
     * @param houseCharacteristicsDTO the entity to save.
     * @return the persisted entity.
     */
    HouseCharacteristicsDTO save(HouseCharacteristicsDTO houseCharacteristicsDTO);

    /**
     * Updates a houseCharacteristics.
     *
     * @param houseCharacteristicsDTO the entity to update.
     * @return the persisted entity.
     */
    HouseCharacteristicsDTO update(HouseCharacteristicsDTO houseCharacteristicsDTO);

    /**
     * Partially updates a houseCharacteristics.
     *
     * @param houseCharacteristicsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HouseCharacteristicsDTO> partialUpdate(HouseCharacteristicsDTO houseCharacteristicsDTO);

    /**
     * Get all the houseCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HouseCharacteristicsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" houseCharacteristics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HouseCharacteristicsDTO> findOne(Long id);

    /**
     * Delete the "id" houseCharacteristics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the houseCharacteristics where Property is {@code null}.
     *
     * @param id    the id of the houseCharacteristics.
     * @param group the characteristics group to filter by.
     * @return the list of houseCharacteristicsDTOs where the specified property is null.
     */
    List<HouseCharacteristicsDTO> findByCharacteristicsByGroupAndProperty(Long id, HouseCharacteristicsGroupEnum group);
}
