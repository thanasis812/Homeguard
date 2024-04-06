package com.hg.service.mapper;

import com.hg.domain.LandLord;
import com.hg.domain.Property;
import com.hg.domain.RentalAgreement;
import com.hg.domain.Tenant;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RentalAgreement} and its DTO {@link RentalAgreementDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalAgreementMapper extends EntityMapper<RentalAgreementDTO, RentalAgreement> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantId")
    @Mapping(target = "propertyOwner", source = "propertyOwner", qualifiedByName = "landLordId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    RentalAgreementDTO toDto(RentalAgreement s);

    @Named("tenantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TenantDTO toDtoTenantId(Tenant tenant);

    @Named("landLordId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LandLordDTO toDtoLandLordId(LandLord landLord);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
