package com.hg.service.dto.mydto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(setterPrefix = "of")
public class UserPropertiesDTO implements Serializable {

    private List<HousePropertyDTO> ownHouses;
    private HousePropertyDTO rentHouse;
}
