package com.hg.service.mapper;

import com.hg.domain.Property;
import com.hg.domain.Tenant;
import com.hg.domain.TenantPropertyPreferences;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.TenantDTO;
import com.hg.service.dto.TenantPropertyPreferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TenantPropertyPreferences} and its DTO {@link TenantPropertyPreferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantPropertyPreferencesMapper extends EntityMapper<TenantPropertyPreferencesDTO, TenantPropertyPreferences> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantId")
    TenantPropertyPreferencesDTO toDto(TenantPropertyPreferences s);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);

    @Named("tenantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TenantDTO toDtoTenantId(Tenant tenant);
}
