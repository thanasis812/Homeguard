package com.hg.service.dto.mydto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminHouseDTO extends PropertyDossierDTO {

    private String privateAgreement;
    private String ownerName;
}
