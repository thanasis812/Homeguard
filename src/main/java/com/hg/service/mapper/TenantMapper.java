package com.hg.service.mapper;

import com.hg.domain.Image;
import com.hg.domain.Location;
import com.hg.domain.Tenant;
import com.hg.domain.User;
import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.LocationDTO;
import com.hg.service.dto.TenantDTO;
import com.hg.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Tenant} and its DTO {@link TenantDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantMapper extends EntityMapper<TenantDTO, Tenant> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "tenantImage", source = "tenantImage", qualifiedByName = "imageId")
    TenantDTO toDto(Tenant s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);
}
