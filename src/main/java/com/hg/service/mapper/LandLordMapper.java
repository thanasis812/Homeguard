package com.hg.service.mapper;

import com.hg.domain.Image;
import com.hg.domain.LandLord;
import com.hg.domain.Tenant;
import com.hg.domain.User;
import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.dto.TenantDTO;
import com.hg.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LandLord} and its DTO {@link LandLordDTO}.
 */
@Mapper(componentModel = "spring")
public interface LandLordMapper extends EntityMapper<LandLordDTO, LandLord> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "tenantId")
    @Mapping(target = "landLordImage", source = "landLordImage", qualifiedByName = "imageId")
    LandLordDTO toDto(LandLord s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("tenantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TenantDTO toDtoTenantId(Tenant tenant);

    @Named("imageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ImageDTO toDtoImageId(Image image);
}
