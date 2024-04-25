package com.hg.service.mapper;

import com.hg.domain.*;
import com.hg.service.dto.*;
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

    @Mapping(target = "rentalAgreement", ignore = true)
    PaymentDTO toDtoPayment(Payment payment);

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
