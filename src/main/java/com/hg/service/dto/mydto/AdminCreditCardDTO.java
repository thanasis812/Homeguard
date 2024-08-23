package com.hg.service.dto.mydto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminCreditCardDTO {

    private Long userId;
    private Long houseId;
    private String fullname;
    private Long cardNumber;
    private Integer cvv;
    private String expireDate;
    private String phone;
}
