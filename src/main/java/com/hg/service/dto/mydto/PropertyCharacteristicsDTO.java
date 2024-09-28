package com.hg.service.dto.mydto;

import com.hg.domain.HouseCharacteristics;
import java.util.List;

/**
 * A DTO for the {@link com.hg.domain.HouseCharacteristics} entity.
 */
public class PropertyCharacteristicsDTO {

    private List<HouseCharacteristics> internal;
    private List<HouseCharacteristics> external;
    private List<HouseCharacteristics> construction;
    private List<HouseCharacteristics> suitableFor;
    private List<HouseCharacteristics> parkingAndAmeinities;
    private List<HouseCharacteristics> view;
    private List<HouseCharacteristics> heatingSystem;
    private List<HouseCharacteristics> GenSelection;

    public List<HouseCharacteristics> getInternal() {
        return internal;
    }

    public void setInternal(List<HouseCharacteristics> internal) {
        this.internal = internal;
    }

    public List<HouseCharacteristics> getExternal() {
        return external;
    }

    public void setExternal(List<HouseCharacteristics> external) {
        this.external = external;
    }

    public List<HouseCharacteristics> getConstruction() {
        return construction;
    }

    public void setConstruction(List<HouseCharacteristics> construction) {
        this.construction = construction;
    }

    public List<HouseCharacteristics> getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(List<HouseCharacteristics> suitableFor) {
        this.suitableFor = suitableFor;
    }

    public List<HouseCharacteristics> getParkingAndAmeinities() {
        return parkingAndAmeinities;
    }

    public void setParkingAndAmeinities(List<HouseCharacteristics> parkingAndAmeinities) {
        this.parkingAndAmeinities = parkingAndAmeinities;
    }

    public List<HouseCharacteristics> getView() {
        return view;
    }

    public void setView(List<HouseCharacteristics> view) {
        this.view = view;
    }

    public List<HouseCharacteristics> getHeatingSystem() {
        return heatingSystem;
    }

    public void setHeatingSystem(List<HouseCharacteristics> heatingSystem) {
        this.heatingSystem = heatingSystem;
    }

    public List<HouseCharacteristics> getGenSelection() {
        return GenSelection;
    }

    public void setGenSelection(List<HouseCharacteristics> genSelection) {
        GenSelection = genSelection;
    }
}
