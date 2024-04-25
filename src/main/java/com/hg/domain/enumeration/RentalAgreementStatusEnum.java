package com.hg.domain.enumeration;

/**
 * The RentalAgreementStatusEnum enumeration.
 * All rental agreement if end must be EXPIRED OR CANCELED
 */
public enum RentalAgreementStatusEnum {
    EXPIRED,
    CANCELED,
    PENDING_LANDLORD_SIGN,
    BOOKED,
    EXPIRED_RENEWED,
    ACTIVE,
}
