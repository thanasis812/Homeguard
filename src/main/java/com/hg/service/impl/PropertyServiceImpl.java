package com.hg.service.impl;

import com.hg.domain.Property;
import com.hg.repository.PropertyRepository;
import com.hg.service.PropertyService;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.mapper.PropertyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.hg.domain.Property}.
 */
@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyServiceImpl.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public PropertyDTO update(PropertyDTO propertyDTO) {
        log.debug("Request to update Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public Optional<PropertyDTO> partialUpdate(PropertyDTO propertyDTO) {
        log.debug("Request to partially update Property : {}", propertyDTO);

        return propertyRepository
            .findById(propertyDTO.getId())
            .map(existingProperty -> {
                propertyMapper.partialUpdate(existingProperty, propertyDTO);

                return existingProperty;
            })
            .map(propertyRepository::save)
            .map(propertyMapper::toDto);
    }

    /**
     *  Get all the properties where TenantPropertyPreferences is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PropertyDTO> findAllWhereTenantPropertyPreferencesIsNull() {
        log.debug("Request to get all properties where TenantPropertyPreferences is null");
        return StreamSupport.stream(propertyRepository.findAll().spliterator(), false)
            .filter(property -> property.getTenantPropertyPreferences() == null)
            .map(propertyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PropertyDTO> findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findById(id).map(propertyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }
}
