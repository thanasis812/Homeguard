package com.hg.service.impl;

import com.hg.domain.TenantPropertyPreferences;
import com.hg.repository.TenantPropertyPreferencesRepository;
import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.mapper.TenantPropertyPreferencesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.TenantPropertyPreferences}.
 */
@Service
@Transactional
public class TenantPropertyPreferencesServiceImpl implements TenantPropertyPreferencesService {

    private final Logger log = LoggerFactory.getLogger(TenantPropertyPreferencesServiceImpl.class);

    private final TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository;

    private final TenantPropertyPreferencesMapper tenantPropertyPreferencesMapper;

    public TenantPropertyPreferencesServiceImpl(
        TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository,
        TenantPropertyPreferencesMapper tenantPropertyPreferencesMapper
    ) {
        this.tenantPropertyPreferencesRepository = tenantPropertyPreferencesRepository;
        this.tenantPropertyPreferencesMapper = tenantPropertyPreferencesMapper;
    }

    @Override
    public TenantPropertyPreferencesDTO save(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO) {
        log.debug("Request to save TenantPropertyPreferences : {}", tenantPropertyPreferencesDTO);
        TenantPropertyPreferences tenantPropertyPreferences = tenantPropertyPreferencesMapper.toEntity(tenantPropertyPreferencesDTO);
        tenantPropertyPreferences = tenantPropertyPreferencesRepository.save(tenantPropertyPreferences);
        return tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);
    }

    @Override
    public TenantPropertyPreferencesDTO update(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO) {
        log.debug("Request to update TenantPropertyPreferences : {}", tenantPropertyPreferencesDTO);
        TenantPropertyPreferences tenantPropertyPreferences = tenantPropertyPreferencesMapper.toEntity(tenantPropertyPreferencesDTO);
        tenantPropertyPreferences = tenantPropertyPreferencesRepository.save(tenantPropertyPreferences);
        return tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);
    }

    @Override
    public Optional<TenantPropertyPreferencesDTO> partialUpdate(TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO) {
        log.debug("Request to partially update TenantPropertyPreferences : {}", tenantPropertyPreferencesDTO);

        return tenantPropertyPreferencesRepository
            .findById(tenantPropertyPreferencesDTO.getId())
            .map(existingTenantPropertyPreferences -> {
                tenantPropertyPreferencesMapper.partialUpdate(existingTenantPropertyPreferences, tenantPropertyPreferencesDTO);

                return existingTenantPropertyPreferences;
            })
            .map(tenantPropertyPreferencesRepository::save)
            .map(tenantPropertyPreferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantPropertyPreferencesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantPropertyPreferences");
        return tenantPropertyPreferencesRepository.findAll(pageable).map(tenantPropertyPreferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantPropertyPreferencesDTO> findOne(Long id) {
        log.debug("Request to get TenantPropertyPreferences : {}", id);
        return tenantPropertyPreferencesRepository.findById(id).map(tenantPropertyPreferencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantPropertyPreferences : {}", id);
        tenantPropertyPreferencesRepository.deleteById(id);
    }
}
