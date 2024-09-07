package com.hg.service.mapper;

import com.hg.domain.ApplicationRequest;
import com.hg.domain.Property;
import com.hg.domain.User;
import com.hg.service.dto.ApplicationRequestDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationRequest} and its DTO {@link ApplicationRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationRequestMapper extends EntityMapper<ApplicationRequestDTO, ApplicationRequest> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    ApplicationRequestDTO toDto(ApplicationRequest s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
