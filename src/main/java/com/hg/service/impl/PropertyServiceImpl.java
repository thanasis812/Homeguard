package com.hg.service.impl;

import com.hg.domain.LandLord;
import com.hg.domain.Property;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.repository.HouseCharacteristicsRepository;
import com.hg.repository.LandLordRepository;
import com.hg.repository.PropertyRepository;
import com.hg.repository.RentalAgreementRepository;
import com.hg.service.PropertyService;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.LocationDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.*;
import com.hg.service.mapper.HouseCharacteristicsMapper;
import com.hg.service.mapper.PropertyMapper;
import com.hg.web.rest.errors.BaseException;
import com.hg.web.rest.errors.NotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    private final RentalAgreementRepository rentalAgreementRepository;
    private final HouseCharacteristicsMapper houseCharacteristicsMapper;
    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(
        PropertyRepository propertyRepository,
        LandLordRepository landlordRepository,
        HouseCharacteristicsRepository houseCharacteristicsRepository,
        RentalAgreementRepository rentalAgreementRepository,
        HouseCharacteristicsMapper houseCharacteristicsMapper,
        PropertyMapper propertyMapper
    ) {
        this.propertyRepository = propertyRepository;
        this.landlordRepository = landlordRepository;
        this.houseCharacteristicsRepository = houseCharacteristicsRepository;
        this.rentalAgreementRepository = rentalAgreementRepository;
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
    public Optional<PropertyDossierDTO> findOneDto(Long id) {
        return propertyRepository.findById(id).map(propertyMapper::toUiDto);
    }

    @Override
    public Optional<UserPropertiesDTO> findUserProperties(Long id, Long userId) {
        var rentedHouses = rentalAgreementRepository
            .findByStatusAndTenant(RentalAgreementStatusEnum.ACTIVE, id)
            .stream()
            .findFirst()
            .orElseThrow(() -> new BaseException("There can be only one rented house", "", ""));
        //todo how to find this?
        var ownedHouses = propertyRepository.findByUserId(id);
        return Optional.of(
            UserPropertiesDTO.builder()
                .ofRentHouse(propertyMapper.toUiDto(rentedHouses.getProperty()))
                .ofOwnHouses(Collections.singletonList(propertyMapper.toUiDto(rentedHouses.getProperty())))
                .build()
        );
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

    @Override
    public AdminHouseDTO newHouseApprove(Long tenantId) {
        var property = propertyRepository.findById(1L).orElse(null);
        AdminHouseDTO adminHouseDTO = new AdminHouseDTO();
        adminHouseDTO.setId(1L);
        adminHouseDTO.setVerified(true);
        adminHouseDTO.setOwnerId(101L);
        adminHouseDTO.setTenantId(202L);
        adminHouseDTO.setTenantGuarantee(303L);
        adminHouseDTO.setName("Beautiful Villa");
        adminHouseDTO.setPrice(new BigDecimal("1200000.00"));
        adminHouseDTO.setDescription("A luxurious villa with stunning views.");
        adminHouseDTO.setSquareMeters(new BigDecimal("350.00"));
        adminHouseDTO.setPlotSquareMeters(new BigDecimal("500.00"));

        // Dummy house characteristics
        Set<PropertyCharacteristicsDTO> characteristics = new HashSet<>();
        PropertyCharacteristicsDTO characteristic = new PropertyCharacteristicsDTO();
        // Set properties for characteristic if needed
        characteristics.add(characteristic);
        adminHouseDTO.setCharacteristics(characteristics);

        adminHouseDTO.setNumberOfBathrooms(3);
        adminHouseDTO.setNumberOfBedrooms(5);
        adminHouseDTO.setNumberOfKitchens(1);
        adminHouseDTO.setNumberOfAirConditioner(4);
        adminHouseDTO.setHouseRules("No loud music after 10 PM.");
        adminHouseDTO.setContractYears(2);
        adminHouseDTO.setNextAvailableDateForRent(LocalDate.now().plusMonths(3));

        adminHouseDTO.setThumbnail(505L);
        adminHouseDTO.setHouseType("Villa");
        adminHouseDTO.setFloor(2);
        adminHouseDTO.setNumberOfFlats(1);
        adminHouseDTO.setEnergyClass("A+");
        adminHouseDTO.setYearOfManufacture(2021);
        adminHouseDTO.setYearOfRenovation(2023);
        adminHouseDTO.setPropertyCode("VILLA-123");
        adminHouseDTO.setFurnitured(true);
        adminHouseDTO.setFurnituredDescription("Fully furnished with modern amenities.");
        adminHouseDTO.setDeleted(false);

        // Dummy address data
        LocationDTO address = new LocationDTO();
        // Set properties for address if needed
        adminHouseDTO.setAddress(address);

        // Dummy images
        Set<byte[]> images = new HashSet<>();
        images.add(new byte[] { /* some byte data */ });
        adminHouseDTO.setImages(images);

        // Dummy reviews
        Set<UserReviewDTO> reviews = new HashSet<>();
        UserReviewDTO review = new UserReviewDTO();
        // Set properties for review if needed
        reviews.add(review);
        adminHouseDTO.setReviews(reviews);
        adminHouseDTO.setOwnerName("admin");
        adminHouseDTO.setPrivateAgreement("private agreement");
        return adminHouseDTO;
    }

    @Override
    public Optional<LandLordHouseInfo> getLandlordHouseInfo(Long houseId) {
        return propertyRepository
            .findById(houseId)
            .map(houseInfo -> {
                Assert.notNull(houseInfo.getLandLord(), () -> "LandLord not found");
                Assert.notNull(houseInfo.getLandLord().getUser(), () -> "LandLord is null");
                return LandLordHouseInfo.builder()
                    .ofImage(houseInfo.getLandLord().getUser().getImageUrl())
                    .ofFirstName(houseInfo.getLandLord().getUser().getFirstName())
                    .ofLastName(houseInfo.getLandLord().getUser().getLastName())
                    .build();
            });
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
