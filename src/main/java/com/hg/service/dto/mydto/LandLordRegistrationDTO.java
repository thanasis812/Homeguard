package com.hg.service.dto.mydto;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandLordRegistrationDTO implements Serializable {

    private TaxIdCertificateAndId taxIdCertificateAndId;
    private LandlordInfoAndBankDetails landlordInfoAndBankDetails;
}

@Getter
@Setter
class TaxIdCertificateAndId implements Serializable {

    private String taxidCertificate;
    private String taxId;
    private String idCertificateFront;
    private String idCertificateBack;
    private String personalId;
}

@Getter
@Setter
class LandlordInfoAndBankDetails implements Serializable {

    private String name;
    private String surname;
    private String address;
    private String city;
    private String postalCode;
    private String iban;
    private String phone;
}
