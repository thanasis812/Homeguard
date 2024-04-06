package com.hg.service.mapper;

import com.hg.domain.Location;
import com.hg.domain.Property;
import com.hg.service.dto.LocationDTO;
import com.hg.service.dto.PropertyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Property} and its DTO {@link PropertyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    PropertyDTO toDto(Property s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
