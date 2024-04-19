package com.hg.service.mapper;

import com.hg.domain.Payment;
import com.hg.domain.RentalAgreement;
import com.hg.service.dto.PaymentDTO;
import com.hg.service.dto.RentalAgreementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "rentalAgreement", source = "rentalAgreement", qualifiedByName = "rentalAgreementId")
    PaymentDTO toDto(Payment s);

    @Named("rentalAgreementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RentalAgreementDTO toDtoRentalAgreementId(RentalAgreement rentalAgreement);

    @Named("rentalAgreementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Payment toDtoRentalAgreementId(RentalAgreement rentalAgreement);
}
