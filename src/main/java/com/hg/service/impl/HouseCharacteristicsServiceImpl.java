package com.hg.service.impl;

import com.hg.domain.HouseCharacteristics;
import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.service.HouseCharacteristicsService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.mapper.HouseCharacteristicsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.HouseCharacteristics}.
 */
@Service
@Transactional
public class HouseCharacteristicsServiceImpl implements HouseCharacteristicsService {

    private final Logger log = LoggerFactory.getLogger(HouseCharacteristicsServiceImpl.class);

    private final HouseCharacteristicsRepository houseCharacteristicsRepository;

    private final HouseCharacteristicsMapper houseCharacteristicsMapper;

    public HouseCharacteristicsServiceImpl(
        HouseCharacteristicsRepository houseCharacteristicsRepository,
        HouseCharacteristicsMapper houseCharacteristicsMapper
    ) {
        this.houseCharacteristicsRepository = houseCharacteristicsRepository;
        this.houseCharacteristicsMapper = houseCharacteristicsMapper;
    }

    @Override
    public HouseCharacteristicsDTO save(HouseCharacteristicsDTO houseCharacteristicsDTO) {
        log.debug("Request to save HouseCharacteristics : {}", houseCharacteristicsDTO);
        HouseCharacteristics houseCharacteristics = houseCharacteristicsMapper.toEntity(houseCharacteristicsDTO);
        houseCharacteristics = houseCharacteristicsRepository.save(houseCharacteristics);
        return houseCharacteristicsMapper.toDto(houseCharacteristics);
    }

    @Override
    public HouseCharacteristicsDTO update(HouseCharacteristicsDTO houseCharacteristicsDTO) {
        log.debug("Request to update HouseCharacteristics : {}", houseCharacteristicsDTO);
        HouseCharacteristics houseCharacteristics = houseCharacteristicsMapper.toEntity(houseCharacteristicsDTO);
        houseCharacteristics = houseCharacteristicsRepository.save(houseCharacteristics);
        return houseCharacteristicsMapper.toDto(houseCharacteristics);
    }

    @Override
    public Optional<HouseCharacteristicsDTO> partialUpdate(HouseCharacteristicsDTO houseCharacteristicsDTO) {
        log.debug("Request to partially update HouseCharacteristics : {}", houseCharacteristicsDTO);

        return houseCharacteristicsRepository
            .findById(houseCharacteristicsDTO.getId())
            .map(existingHouseCharacteristics -> {
                houseCharacteristicsMapper.partialUpdate(existingHouseCharacteristics, houseCharacteristicsDTO);

                return existingHouseCharacteristics;
            })
            .map(houseCharacteristicsRepository::save)
            .map(houseCharacteristicsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HouseCharacteristicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HouseCharacteristics");
        return houseCharacteristicsRepository.findAll(pageable).map(houseCharacteristicsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HouseCharacteristicsDTO> findOne(Long id) {
        log.debug("Request to get HouseCharacteristics : {}", id);
        return houseCharacteristicsRepository.findById(id).map(houseCharacteristicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HouseCharacteristics : {}", id);
        houseCharacteristicsRepository.deleteById(id);
    }
}
