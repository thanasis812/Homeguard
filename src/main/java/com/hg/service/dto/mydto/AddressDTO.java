package com.hg.service.dto.mydto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDTO implements Serializable {

    private String address;
    private String city;
    private String postalCode;
}
