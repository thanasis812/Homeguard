package com.hg.service.mapper;

import com.hg.domain.Location;
import com.hg.domain.Property;
import com.hg.domain.RentalAgreement;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.service.dto.LocationDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.HousePropertyDTO;
import com.hg.service.dto.mydto.NewHouseRequestDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Property} and its DTO {@link PropertyDTO}.
 */
@Mapper(componentModel = "spring", uses = { HouseCharacteristicsMapper.class, ImageMapper.class, ReviewMapper.class })
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    PropertyDTO toDto(Property s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    //    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "ownerId", source = "property", qualifiedByName = "extractLandLordId")
    @Mapping(target = "characteristics", source = "houseCharacteristics", qualifiedByName = "toDtoList")
    @Mapping(target = "images", source = "propertysPhotos", qualifiedByName = "toByteList")
    @Mapping(target = "availability", source = "property", qualifiedByName = "mapAvailability")
    //    @Mapping(target = "monthsPaid", source = "property", qualifiedByName = "mapMonthsPaid")
    //    @Mapping(target = "reviews", source = "reviews", qualifiedByName = "toUserDtoList")
    PropertyDossierDTO toUiDto(Property property);

    @Named("toDtoList")
    default List<PropertyDossierDTO> toDtoList(List<Property> source) {
        return source.stream().map(this::toUiDto).collect(Collectors.toList());
    }

    @Named("extractLandLordId")
    default Long extractLandLordId(Property property) {
        return property != null && property.getLandLord() != null && property.getLandLord().getId() != null
            ? property.getLandLord().getId()
            : null;
    }

    @Named("mapAvailability")
    default PropertyDossierDTO.AvailabilityDto mapAvailability(Property property) {
        PropertyDossierDTO.AvailabilityDto availability = new PropertyDossierDTO.AvailabilityDto();
        if (Objects.nonNull(property) && !property.getRentals().isEmpty()) {
            Optional<RentalAgreement> rentalAgreementOptional = property
                .getRentals()
                .stream()
                .max(Comparator.comparing(RentalAgreement::getCreatedDate));
            var rentalAgreement = rentalAgreementOptional.orElseThrow();

            if (rentalAgreementOptional.filter(status -> status.getStatus().equals(RentalAgreementStatusEnum.ACTIVE)).isPresent()) {
                availability.setAvailableFrom(rentalAgreement.getExpirationDate());
                availability.setLastUpdate(rentalAgreement.getLatest());
                availability.setCurrentlyRented("RENTED");
            }
            availability.setAvailableFrom(rentalAgreement.getExpirationDate());
            availability.setCurrentlyRented("FREE");
            availability.setLastUpdate(rentalAgreement.getLatest());
        }
        return availability;
    }

    @Mapping(source = "newHouseRequestDTO", target = "location", qualifiedByName = "mapToLocation")
    Property toInternalSchemaNewProperty(NewHouseRequestDTO newHouseRequestDTO);

    NewHouseRequestDTO toExternalSchemaNewProperty(Property property);

    @Named("mapToLocation")
    default Location mapToLocation(NewHouseRequestDTO source) {
        if (source == null) {
            return null;
        }
        Location location = new Location();
        location.setCity(source.getCity());
        location.setPostalCode(source.getPostalCode());
        location.setLongitude(source.getLongitude());
        location.setLatitude(source.getLatitude());
        location.setZipCode(source.getZipCode());
        location.setCountry(source.getCountry());
        location.setStreetAddress(source.getAddress());
        return location;
    }

    @Mappings(
        {
            @Mapping(source = "rentedHouses.property.id", target = "houseId"),
            @Mapping(source = "rentedHouses.property.tenantPropertyPreferences.favorite", target = "favorite"),
            @Mapping(source = "rentedHouses.property.tenantPropertyPreferences.reminder", target = "reminder"),
            @Mapping(source = "rentedHouses.tenant.id", target = "tenantId"),
        }
    )
    HousePropertyDTO toDto(RentalAgreement rentedHouses);
}
