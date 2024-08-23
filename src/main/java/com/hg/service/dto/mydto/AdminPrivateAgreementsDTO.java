package com.hg.service.dto.mydto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminPrivateAgreementsDTO {

    private Long id;
    private Long houseId;
    private Long number;
    private String ownerName;
    private String tenantName;
    private String name;
    private int price;
    private String generalTerms;
    private String landlordTerms;
    private Long lanlordSignature;
    private Long tenantSignature;
}
