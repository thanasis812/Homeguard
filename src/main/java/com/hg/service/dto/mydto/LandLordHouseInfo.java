package com.hg.service.dto.mydto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "of")
public class LandLordHouseInfo {

    private String firstName;
    private String lastName;
    private String image;
}
