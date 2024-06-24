package com.hg.service.impl;

import com.hg.domain.TenantPropertyPreferences;
import com.hg.repository.PropertyRepository;
import com.hg.repository.TenantPropertyPreferencesRepository;
import com.hg.repository.TenantRepository;
import com.hg.service.TenantPropertyPreferencesService;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import com.hg.service.dto.mydto.FavoritePropertyDTO;
import com.hg.service.mapper.PropertyMapper;
import com.hg.service.mapper.TenantPropertyPreferencesMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final PropertyMapper propertyMapper;
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;

    public TenantPropertyPreferencesServiceImpl(
        TenantPropertyPreferencesRepository tenantPropertyPreferencesRepository,
        TenantPropertyPreferencesMapper tenantPropertyPreferencesMapper,
        PropertyMapper propertyMapper,
        TenantRepository tenantRepository,
        PropertyRepository propertyRepository
    ) {
        this.tenantPropertyPreferencesRepository = tenantPropertyPreferencesRepository;
        this.tenantPropertyPreferencesMapper = tenantPropertyPreferencesMapper;
        this.propertyMapper = propertyMapper;
        this.tenantRepository = tenantRepository;
        this.propertyRepository = propertyRepository;
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

    @Override
    public FavoritePropertyDTO getFavouriteAndAlarmHouses(Long tenantId) {
        List<TenantPropertyPreferences> tenantPropertyPreferences =
            tenantPropertyPreferencesRepository.findTenantPropertyPreferencesByTenantId(tenantId);
        FavoritePropertyDTO favoritePropertyDTO = new FavoritePropertyDTO();
        favoritePropertyDTO.setAlarm(
            propertyMapper.toDtoList(
                tenantPropertyPreferences
                    .stream()
                    .filter(TenantPropertyPreferences::getReminder)
                    .map(TenantPropertyPreferences::getProperty)
                    .collect(Collectors.toList())
            )
        );
        favoritePropertyDTO.setFavourites(
            propertyMapper.toDtoList(
                tenantPropertyPreferences
                    .stream()
                    .filter(TenantPropertyPreferences::getFavorite)
                    .map(TenantPropertyPreferences::getProperty)
                    .collect(Collectors.toList())
            )
        );
        return favoritePropertyDTO;
    }

    @Override
    public TenantPropertyPreferencesDTO favoritePropertyForTenant(Long houseId, Long tenantId, boolean favorite) {
        return favoriteThePropertyForTenant(houseId, tenantId, favorite);
    }

    @Override
    public TenantPropertyPreferencesDTO alarmPropertyForTenant(Long houseId, Long tenantId, boolean reminder) {
        return changeReminderOfProperty(houseId, tenantId, reminder);
    }

    private TenantPropertyPreferencesDTO favoriteThePropertyForTenant(Long houseId, Long tenantId, boolean isFavorite) {
        var property = tenantPropertyPreferencesRepository.findTenantPropertyPreferencesByTenantIdAndHouseId(tenantId, houseId);
        if (property.isPresent()) {
            TenantPropertyPreferences tenantPropertyPreferences = property.orElseThrow();
            tenantPropertyPreferences.setFavorite(isFavorite);
            tenantPropertyPreferences.setFavoriteDate(LocalDate.now());
            tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferencesRepository.save(tenantPropertyPreferences));
        } else {
            TenantPropertyPreferences tenantPropertyPreferences = new TenantPropertyPreferences();
            tenantPropertyPreferences.setFavorite(isFavorite);
            tenantPropertyPreferences.setFavoriteDate(LocalDate.now());
            tenantPropertyPreferences.setTenant(tenantRepository.findById(tenantId).orElseThrow());
            tenantPropertyPreferences.setProperty(propertyRepository.findById(houseId).orElseThrow());
            tenantPropertyPreferencesRepository.save(tenantPropertyPreferences);
            return tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);
        }
        return null;
    }

    private TenantPropertyPreferencesDTO changeReminderOfProperty(Long houseId, Long tenantId, boolean isReminder) {
        var property = tenantPropertyPreferencesRepository.findTenantPropertyPreferencesByTenantIdAndHouseId(tenantId, houseId);
        if (property.isPresent()) {
            TenantPropertyPreferences tenantPropertyPreferences = property.orElseThrow();
            tenantPropertyPreferences.setReminder(isReminder);
            tenantPropertyPreferences.setReminderDate(LocalDate.now());
            tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferencesRepository.save(tenantPropertyPreferences));
        } else {
            TenantPropertyPreferences tenantPropertyPreferences = new TenantPropertyPreferences();
            tenantPropertyPreferences.setReminder(isReminder);
            tenantPropertyPreferences.setReminderDate(LocalDate.now());
            tenantPropertyPreferences.setTenant(tenantRepository.findById(tenantId).orElseThrow());
            tenantPropertyPreferences.setProperty(propertyRepository.findById(houseId).orElseThrow());
            tenantPropertyPreferencesRepository.save(tenantPropertyPreferences);
            return tenantPropertyPreferencesMapper.toDto(tenantPropertyPreferences);
        }
        return null;
    }
}
