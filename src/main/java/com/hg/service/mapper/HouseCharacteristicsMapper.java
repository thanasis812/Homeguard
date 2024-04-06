package com.hg.service.mapper;

import com.hg.domain.HouseCharacteristics;
import com.hg.domain.Property;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.PropertyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HouseCharacteristics} and its DTO {@link HouseCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring")
public interface HouseCharacteristicsMapper extends EntityMapper<HouseCharacteristicsDTO, HouseCharacteristics> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    HouseCharacteristicsDTO toDto(HouseCharacteristics s);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
