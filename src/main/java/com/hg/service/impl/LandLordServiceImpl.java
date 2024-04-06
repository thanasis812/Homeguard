package com.hg.service.impl;

import com.hg.domain.LandLord;
import com.hg.repository.LandLordRepository;
import com.hg.service.LandLordService;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.mapper.LandLordMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.LandLord}.
 */
@Service
@Transactional
public class LandLordServiceImpl implements LandLordService {

    private final Logger log = LoggerFactory.getLogger(LandLordServiceImpl.class);

    private final LandLordRepository landLordRepository;

    private final LandLordMapper landLordMapper;

    public LandLordServiceImpl(LandLordRepository landLordRepository, LandLordMapper landLordMapper) {
        this.landLordRepository = landLordRepository;
        this.landLordMapper = landLordMapper;
    }

    @Override
    public LandLordDTO save(LandLordDTO landLordDTO) {
        log.debug("Request to save LandLord : {}", landLordDTO);
        LandLord landLord = landLordMapper.toEntity(landLordDTO);
        landLord = landLordRepository.save(landLord);
        return landLordMapper.toDto(landLord);
    }

    @Override
    public LandLordDTO update(LandLordDTO landLordDTO) {
        log.debug("Request to update LandLord : {}", landLordDTO);
        LandLord landLord = landLordMapper.toEntity(landLordDTO);
        landLord = landLordRepository.save(landLord);
        return landLordMapper.toDto(landLord);
    }

    @Override
    public Optional<LandLordDTO> partialUpdate(LandLordDTO landLordDTO) {
        log.debug("Request to partially update LandLord : {}", landLordDTO);

        return landLordRepository
            .findById(landLordDTO.getId())
            .map(existingLandLord -> {
                landLordMapper.partialUpdate(existingLandLord, landLordDTO);

                return existingLandLord;
            })
            .map(landLordRepository::save)
            .map(landLordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandLordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LandLords");
        return landLordRepository.findAll(pageable).map(landLordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LandLordDTO> findOne(Long id) {
        log.debug("Request to get LandLord : {}", id);
        return landLordRepository.findById(id).map(landLordMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LandLord : {}", id);
        landLordRepository.deleteById(id);
    }
}
