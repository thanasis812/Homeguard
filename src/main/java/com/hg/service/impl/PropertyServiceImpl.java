package com.hg.service.impl;

import com.hg.domain.LandLord;
import com.hg.domain.Property;
import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.repository.LandLordRepository;
import com.hg.repository.PropertyRepository;
import com.hg.service.PropertyService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.NewHouseRequestDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import com.hg.service.mapper.HouseCharacteristicsMapper;
import com.hg.service.mapper.PropertyMapper;
import com.hg.web.rest.errors.NotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final LandLordRepository landlordRepository;
    private final HouseCharacteristicsRepository houseCharacteristicsRepository;
    private final HouseCharacteristicsMapper houseCharacteristicsMapper;
    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(
        PropertyRepository propertyRepository,
        LandLordRepository landlordRepository,
        HouseCharacteristicsRepository houseCharacteristicsRepository,
        HouseCharacteristicsMapper houseCharacteristicsMapper,
        PropertyMapper propertyMapper
    ) {
        this.propertyRepository = propertyRepository;
        this.landlordRepository = landlordRepository;
        this.houseCharacteristicsRepository = houseCharacteristicsRepository;
        this.houseCharacteristicsMapper = houseCharacteristicsMapper;
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
     * Get all the properties where TenantPropertyPreferences is {@code null}.
     *
     * @return the list of entities.
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
    public Optional<PropertyDossierDTO> getPropertyById(Long tenantId) {
        log.debug("Request to get Property for tenant Id : {}", tenantId);
        return propertyRepository.findById(tenantId).map(propertyMapper::toUiDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }

    @Override
    public List<PropertyDossierDTO> getUserProperties(Long landlordId) {
        log.debug("Request to get getUserProperties for landlordId : {}", landlordId);
        return propertyRepository
            .findByLandLord(
                landlordRepository
                    .findById(landlordId)
                    .orElseThrow(() -> new NotFoundException(String.format("Can't find LandLord with Landlord id: %d", landlordId)))
            )
            .stream()
            .map(propertyMapper::toUiDto)
            .collect(Collectors.toList());
    }

    @Override
    public Boolean isCertified(Long propertyId, Long landlordId) {
        return propertyRepository
            .findById(propertyId)
            .map(property -> {
                LandLord landlord = property.getLandLord();
                return landlord != null && landlord.getId() != null && landlord.getId().equals(landlordId);
            })
            .orElse(false);
    }

    @Override
    public NewHouseRequestDTO save(NewHouseRequestDTO newHouseRequestDTO) {
        var landLord = landlordRepository
            .findById(newHouseRequestDTO.getOwnerId())
            .orElseThrow(
                () -> new NotFoundException(String.format("Can't find LandLord with Landlord id: %d", newHouseRequestDTO.getOwnerId()))
            );

        var toSaveObject = propertyMapper.toInternalSchemaNewProperty(newHouseRequestDTO);
        toSaveObject.setLandLord(landLord);
        return propertyMapper.toExternalSchemaNewProperty(propertyRepository.save(toSaveObject));
    }

    private void saveCharacteristics(NewHouseRequestDTO newHouseRequestDTO, NewHouseRequestDTO savedObject) {
        savedObject.getCharacteristics().setConstruction(getList(newHouseRequestDTO.getCharacteristics().getConstruction()));
        savedObject.getCharacteristics().setView(getList(newHouseRequestDTO.getCharacteristics().getView()));
        savedObject.getCharacteristics().setInternal(getList(newHouseRequestDTO.getCharacteristics().getInternal()));
        savedObject.getCharacteristics().setExternal(getList(newHouseRequestDTO.getCharacteristics().getExternal()));
        savedObject
            .getCharacteristics()
            .setParkingAndAmeinities(getList(newHouseRequestDTO.getCharacteristics().getParkingAndAmeinities()));
        savedObject.getCharacteristics().setSuitableFor(getList(newHouseRequestDTO.getCharacteristics().getSuitableFor()));
    }

    private List<HouseCharacteristicsDTO> getList(List<HouseCharacteristicsDTO> newHouseRequestDTO) {
        return getDtoList(newHouseRequestDTO).stream().toList();
    }

    private Set<HouseCharacteristicsDTO> getDtoList(List<HouseCharacteristicsDTO> newHouseRequestDTO) {
        return houseCharacteristicsMapper.toDtoList(
            houseCharacteristicsRepository.saveAll(houseCharacteristicsMapper.toEntity(newHouseRequestDTO))
        );
    }
}
