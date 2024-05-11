package com.hg.service.dto.mydto;

import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hg.domain.RentalAgreement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalApplicationStatusDTO implements Serializable {

    private RentalAgreementStatusEnum salaryCertificate;
    private RentalAgreementStatusEnum taxidCertificateAndId;
    private RentalAgreementStatusEnum creditCard;
    private RentalAgreementStatusEnum privateAgreement;

    public RentalAgreementStatusEnum getSalaryCertificate() {
        return salaryCertificate;
    }

    public void setSalaryCertificate(RentalAgreementStatusEnum salaryCertificate) {
        this.salaryCertificate = salaryCertificate;
    }

    public RentalAgreementStatusEnum getTaxidCertificateAndId() {
        return taxidCertificateAndId;
    }

    public void setTaxidCertificateAndId(RentalAgreementStatusEnum taxidCertificateAndId) {
        this.taxidCertificateAndId = taxidCertificateAndId;
    }

    public RentalAgreementStatusEnum getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(RentalAgreementStatusEnum creditCard) {
        this.creditCard = creditCard;
    }

    public RentalAgreementStatusEnum getPrivateAgreement() {
        return privateAgreement;
    }

    public void setPrivateAgreement(RentalAgreementStatusEnum privateAgreement) {
        this.privateAgreement = privateAgreement;
    }
}
