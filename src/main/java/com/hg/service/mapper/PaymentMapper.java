package com.hg.service.mapper;

import com.hg.domain.Payment;
import com.hg.domain.RentalAgreement;
import com.hg.service.dto.PaymentDTO;
import com.hg.service.dto.RentalAgreementDTO;
import com.hg.service.dto.mydto.UserPaymentDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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

    List<UserPaymentDTO> toUserPaymentDTOList(List<Payment> payment);

    @Mapping(target = "houseId", source = "payment", qualifiedByName = "extractHouseId")
    @Mapping(target = "paymentDate", source = "payedDate", qualifiedByName = "instantToLocalDate")
    UserPaymentDTO toUserPaymentDTO(Payment payment);

    @Named("extractHouseId")
    default Long extractHouseId(Payment payment) {
        return payment != null && payment.getRentalAgreement() != null && payment.getRentalAgreement().getProperty() != null
            ? payment.getRentalAgreement().getProperty().getId()
            : null;
    }

    @Named("instantToLocalDate")
    default LocalDate instantToLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
