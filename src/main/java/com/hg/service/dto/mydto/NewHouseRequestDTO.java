package com.hg.service.dto.mydto;

import com.hg.service.dto.ImageDTO;
import com.hg.service.dto.LocationDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link com.hg.domain.Property} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NewHouseRequestDTO implements Serializable {

    private Long id;
    private BigDecimal price;
    private String name;
    private LocationDTO location;
    private String description;
    private BigDecimal squareMeters;
    private LocalDate startedRentingDate;
    private ImageDTO thumbnail;
    private List<ImageDTO> houseImages;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Integer numberOfKitchens;
    private Integer numberOfAirConditioner;
    private BigDecimal plotSquareMeters;
    private BigDecimal pricePerSquareMeter;
    private Integer yearOfManufacture;
    private Integer yearOfRenovation;
    private Integer floor;
    private Boolean furnitured;
    private String furnituredDescription;
    private NewHouseCharacteristicsDTO characteristics;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
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

    public BigDecimal getPlotSquareMeters() {
        return plotSquareMeters;
    }

    public void setPlotSquareMeters(BigDecimal plotSquareMeters) {
        this.plotSquareMeters = plotSquareMeters;
    }

    public BigDecimal getPricePerSquareMeter() {
        return pricePerSquareMeter;
    }

    public void setPricePerSquareMeter(BigDecimal pricePerSquareMeter) {
        this.pricePerSquareMeter = pricePerSquareMeter;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(BigDecimal squareMeters) {
        this.squareMeters = squareMeters;
    }

    public LocalDate getStartedRentingDate() {
        return startedRentingDate;
    }

    public void setStartedRentingDate(LocalDate startedRentingDate) {
        this.startedRentingDate = startedRentingDate;
    }

    public ImageDTO getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageDTO thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<ImageDTO> getHouseImages() {
        return houseImages;
    }

    public void setHouseImages(List<ImageDTO> houseImages) {
        this.houseImages = houseImages;
    }
}
