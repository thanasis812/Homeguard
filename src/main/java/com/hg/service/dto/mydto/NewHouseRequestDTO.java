package com.hg.service.dto.mydto;

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
    private String address;
    private Integer zipCode;
    private String city;
    private String country;
    private String streetAddress;
    private String postalCode;
    private Double longitude;
    private Double latitude;
    private UploadImageDTO energyClassId;
    private String houseType;

    private String energyClass;
    private String description;
    private BigDecimal squareMeters;
    private LocalDate startedRentingDate;
    //    private ImageDTO thumbnail;
    private List<UploadImageDTO> images; //// TODO: 5/25/2024
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
    private NewHouseCharacteristicsDTO characteristics = new NewHouseCharacteristicsDTO();

    private PrivateAgreementsTermsRequestDTO privateAgreement;

    private String ownerName;
    private Long ownerId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public UploadImageDTO getEnergyClassId() {
        return energyClassId;
    }

    public void setEnergyClassId(UploadImageDTO energyClassId) {
        this.energyClassId = energyClassId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getEnergyClass() {
        return energyClass;
    }

    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    public List<UploadImageDTO> getImages() {
        return images;
    }

    public void setImages(List<UploadImageDTO> images) {
        this.images = images;
    }

    public PrivateAgreementsTermsRequestDTO getPrivateAgreement() {
        return privateAgreement;
    }

    public void setPrivateAgreement(PrivateAgreementsTermsRequestDTO privateAgreement) {
        this.privateAgreement = privateAgreement;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

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

    public NewHouseCharacteristicsDTO getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(NewHouseCharacteristicsDTO characteristics) {
        this.characteristics = characteristics;
    }
}
