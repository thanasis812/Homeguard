package com.hg.service.mapper;

import com.hg.domain.LandLord;
import com.hg.domain.Property;
import com.hg.domain.Review;
import com.hg.domain.Tenant;
import com.hg.service.dto.LandLordDTO;
import com.hg.service.dto.PropertyDTO;
import com.hg.service.dto.ReviewDTO;
import com.hg.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantId")
    @Mapping(target = "landLord", source = "landLord", qualifiedByName = "landLordId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    ReviewDTO toDto(Review s);

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
