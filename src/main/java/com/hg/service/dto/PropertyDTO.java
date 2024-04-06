package com.hg.service.dto;

import com.hg.domain.enumeration.ConstructionEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Property} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PropertyDTO implements Serializable {

    private Long id;

    private Boolean verified;

    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private BigDecimal squareMeters;

    private BigDecimal plotSquareMeters;

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private Integer numberOfKitchens;

    private Integer numberOfAirConditioner;

    @Lob
    private String houseRules;

    private Integer contractYears;

    private LocalDate nextAvailableDateForRent;

    private Long thumbnail;

    private String houseType;

    private Integer floor;

    private Integer numberOfFlats;

    private String energyClass;

    private ConstructionEnum construction;

    private Integer yearOfManufacture;

    private Integer yearOfRenovation;

    private String propertyCode;

    private Boolean furnitured;

    private String furnituredDescription;

    private Boolean deleted;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(BigDecimal squareMeters) {
        this.squareMeters = squareMeters;
    }

    public BigDecimal getPlotSquareMeters() {
        return plotSquareMeters;
    }

    public void setPlotSquareMeters(BigDecimal plotSquareMeters) {
        this.plotSquareMeters = plotSquareMeters;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Integer getNumberOfKitchens() {
        return numberOfKitchens;
    }

    public void setNumberOfKitchens(Integer numberOfKitchens) {
        this.numberOfKitchens = numberOfKitchens;
    }

    public Integer getNumberOfAirConditioner() {
        return numberOfAirConditioner;
    }

    public void setNumberOfAirConditioner(Integer numberOfAirConditioner) {
        this.numberOfAirConditioner = numberOfAirConditioner;
    }

    public String getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(String houseRules) {
        this.houseRules = houseRules;
    }

    public Integer getContractYears() {
        return contractYears;
    }

    public void setContractYears(Integer contractYears) {
        this.contractYears = contractYears;
    }

    public LocalDate getNextAvailableDateForRent() {
        return nextAvailableDateForRent;
    }

    public void setNextAvailableDateForRent(LocalDate nextAvailableDateForRent) {
        this.nextAvailableDateForRent = nextAvailableDateForRent;
    }

    public Long getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Long thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getNumberOfFlats() {
        return numberOfFlats;
    }

    public void setNumberOfFlats(Integer numberOfFlats) {
        this.numberOfFlats = numberOfFlats;
    }

    public String getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    public ConstructionEnum getConstruction() {
        return construction;
    }

    public void setConstruction(ConstructionEnum construction) {
        this.construction = construction;
    }

    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Integer getYearOfRenovation() {
        return yearOfRenovation;
    }

    public void setYearOfRenovation(Integer yearOfRenovation) {
        this.yearOfRenovation = yearOfRenovation;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public Boolean getFurnitured() {
        return furnitured;
    }

    public void setFurnitured(Boolean furnitured) {
        this.furnitured = furnitured;
    }

    public String getFurnituredDescription() {
        return furnituredDescription;
    }

    public void setFurnituredDescription(String furnituredDescription) {
        this.furnituredDescription = furnituredDescription;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyDTO)) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, propertyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", verified='" + getVerified() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", squareMeters=" + getSquareMeters() +
            ", plotSquareMeters=" + getPlotSquareMeters() +
            ", numberOfBathrooms=" + getNumberOfBathrooms() +
            ", numberOfBedrooms=" + getNumberOfBedrooms() +
            ", numberOfKitchens=" + getNumberOfKitchens() +
            ", numberOfAirConditioner=" + getNumberOfAirConditioner() +
            ", houseRules='" + getHouseRules() + "'" +
            ", contractYears=" + getContractYears() +
            ", nextAvailableDateForRent='" + getNextAvailableDateForRent() + "'" +
            ", thumbnail=" + getThumbnail() +
            ", houseType='" + getHouseType() + "'" +
            ", floor=" + getFloor() +
            ", numberOfFlats=" + getNumberOfFlats() +
            ", energyClass='" + getEnergyClass() + "'" +
            ", construction='" + getConstruction() + "'" +
            ", yearOfManufacture=" + getYearOfManufacture() +
            ", yearOfRenovation=" + getYearOfRenovation() +
            ", propertyCode='" + getPropertyCode() + "'" +
            ", furnitured='" + getFurnitured() + "'" +
            ", furnituredDescription='" + getFurnituredDescription() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
