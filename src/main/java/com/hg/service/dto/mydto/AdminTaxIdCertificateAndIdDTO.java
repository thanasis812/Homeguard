package com.hg.service.dto.mydto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminTaxIdCertificateAndIdDTO implements Serializable {

    private Long taxidCertificateId;
    private Long idCertificateId;
    private Long userId;
    private byte[] idCertificateFront;
    private byte[] idCertificateBack;
    private byte[] taxidCertificate;
    private Long taxId;
    private String personalId;
    private String name;
    private String surname;
    private byte[] guarantorIdCertificateFront;
    private byte[] guarantorIdCertificateBack;
    private byte[] guarantorTaxidCertificate;
    private Long guarantorTaxId;
    private String guarantorPersonalId;
    private String guarantorName;
    private String guarantorSurname;
}
