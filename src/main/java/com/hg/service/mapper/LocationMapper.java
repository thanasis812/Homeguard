package com.hg.service.mapper;

import com.hg.domain.Location;
import com.hg.service.dto.LocationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {}
