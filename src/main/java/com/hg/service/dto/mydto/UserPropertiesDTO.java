package com.hg.service.dto.mydto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPropertiesDTO implements Serializable {

    private List<HousePropertyDTO> ownHouses = new ArrayList<>();
    private HousePropertyDTO rentHouse;

    public List<HousePropertyDTO> getOwnHouses() {
        return ownHouses;
    }

    public void setOwnHouses(List<HousePropertyDTO> ownHouses) {
        this.ownHouses = ownHouses;
    }

    public HousePropertyDTO getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(HousePropertyDTO rentHouse) {
        this.rentHouse = rentHouse;
    }
}
