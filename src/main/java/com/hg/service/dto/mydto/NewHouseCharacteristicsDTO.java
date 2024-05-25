package com.hg.service.dto.mydto;

import com.hg.domain.enumeration.HouseCharacteristicsEnum;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.PropertyDTO;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.HouseCharacteristics} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NewHouseCharacteristicsDTO implements Serializable {

    List<HouseCharacteristicsDTO> internal;
    List<HouseCharacteristicsDTO> external;
    List<HouseCharacteristicsDTO> construction;
    List<HouseCharacteristicsDTO> suitableFor;
    List<HouseCharacteristicsDTO> parkingAndAmeinities;
    List<HouseCharacteristicsDTO> view;

    public List<HouseCharacteristicsDTO> getInternal() {
        return internal;
    }

    public void setInternal(List<HouseCharacteristicsDTO> internal) {
        this.internal = internal;
    }

    public List<HouseCharacteristicsDTO> getExternal() {
        return external;
    }

    public void setExternal(List<HouseCharacteristicsDTO> external) {
        this.external = external;
    }

    public List<HouseCharacteristicsDTO> getConstruction() {
        return construction;
    }

    public void setConstruction(List<HouseCharacteristicsDTO> construction) {
        this.construction = construction;
    }

    public List<HouseCharacteristicsDTO> getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(List<HouseCharacteristicsDTO> suitableFor) {
        this.suitableFor = suitableFor;
    }

    public List<HouseCharacteristicsDTO> getParkingAndAmeinities() {
        return parkingAndAmeinities;
    }

    public void setParkingAndAmeinities(List<HouseCharacteristicsDTO> parkingAndAmeinities) {
        this.parkingAndAmeinities = parkingAndAmeinities;
    }

    public List<HouseCharacteristicsDTO> getView() {
        return view;
    }

    public void setView(List<HouseCharacteristicsDTO> view) {
        this.view = view;
    }
}
