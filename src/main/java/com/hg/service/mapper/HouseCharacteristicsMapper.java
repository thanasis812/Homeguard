package com.hg.service.mapper;

import com.hg.domain.HouseCharacteristics;
import com.hg.domain.Property;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.PropertyDTO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HouseCharacteristics} and its DTO {@link HouseCharacteristicsDTO}.
 */
@Mapper(componentModel = "spring")
public interface HouseCharacteristicsMapper extends EntityMapper<HouseCharacteristicsDTO, HouseCharacteristics> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    HouseCharacteristicsDTO toDto(HouseCharacteristics s);

    @Named("toDtoList")
    @Mapping(target = "property", ignore = true)
    default Set<HouseCharacteristicsDTO> toDtoList(Set<HouseCharacteristics> s) {
        return s.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Named("toDtoList")
    @Mapping(target = "property", ignore = true)
    default Set<HouseCharacteristicsDTO> toDtoList(List<HouseCharacteristics> s) {
        return s.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
