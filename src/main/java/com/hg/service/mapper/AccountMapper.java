package com.hg.service.mapper;

import com.hg.domain.Location;
import com.hg.domain.Tenant;
import com.hg.domain.User;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import com.hg.service.dto.AdminUserDTO;
import com.hg.service.dto.mydto.AddressDTO;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "emailVerified", source = "activated")
    @Mapping(target = "address", source = "tenant.location", qualifiedByName = "mapAddress")
    @Mapping(target = "subscription", source = "tenant.subscriptionType")
    @Mapping(target = "phoneAvailable", expression = "java(user.getPhone() == null ? false : true)")
    @Mapping(target = "hasBothCategories", expression = "java(user.getTenant() != null && user.getLandlord() !=null)")
    @Mapping(target = "hasRentedHouse", source = "tenant", qualifiedByName = "hasRentedHouse")
    AdminUserDTO toDto(User user);

    @Named("mapAddress")
    default AddressDTO mapAddress(Location location) {
        return Optional.ofNullable(location)
            .map(ad -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setAddress(ad.getStreetAddress());
                addressDTO.setCity(ad.getCity());
                addressDTO.setPostalCode(ad.getPostalCode());
                return addressDTO;
            })
            .orElse(null);
    }

    @Named("hasRentedHouse")
    default boolean hasRentedHouse(Tenant tenant) {
        if (tenant == null || tenant.getRentedPropertysAgreements() == null) return false;
        return tenant
            .getRentedPropertysAgreements()
            .stream()
            .anyMatch(rental -> rental.getStatus().equals(RentalAgreementStatusEnum.ACTIVE));
    }
}
