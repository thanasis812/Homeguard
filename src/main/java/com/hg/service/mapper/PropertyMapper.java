package com.hg.service.mapper;

import com.hg.domain.HouseCharacteristics;
import com.hg.domain.Location;
import com.hg.domain.Property;
import com.hg.domain.RentalAgreement;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.LocationDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.mydto.PropertyDossierDTO;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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

            if (rentalAgreementOptional.filter(staus -> staus.getStatus().equals(RentalAgreementStatusEnum.ACTIVE)).isPresent()) {
                availability.setAvailableFrom(rentalAgreement.getExpirationDate());
                availability.setLastUpdate(rentalAgreement.getLatest());
                availability.setCurrentlyRented("Yes");
            }
            availability.setAvailableFrom(rentalAgreement.getExpirationDate());
            availability.setCurrentlyRented("NO");
            availability.setLastUpdate(rentalAgreement.getLatest());
        }
        return availability;
    }
}
