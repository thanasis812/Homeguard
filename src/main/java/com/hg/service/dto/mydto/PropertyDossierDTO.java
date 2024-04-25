package com.hg.service.dto.mydto;

import com.hg.domain.enumeration.ConstructionEnum;
import com.hg.service.dto.HouseCharacteristicsDTO;
import com.hg.service.dto.LocationDTO;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class PropertyDossierDTO implements Serializable {

    private Long id;

    private Boolean verified;
    private Long ownerId;
    private Long tenantId; // todo why?
    private Long tenantGuarantee;

    private String name;

    private BigDecimal price;
    private String description;

    private BigDecimal squareMeters;

    private BigDecimal plotSquareMeters;

    private Set<HouseCharacteristicsDTO> characteristics = new HashSet<>();

    private Integer numberOfBathrooms;

    private Integer numberOfBedrooms;

    private Integer numberOfKitchens;

    private Integer numberOfAirConditioner;

    @Lob
    private String houseRules;

    private Integer contractYears;

    private LocalDate nextAvailableDateForRent;

    private AvailabilityDto availability;

    private Long thumbnail;

    private String houseType;

    private Integer floor;

    private Integer numberOfFlats;

    private String energyClass;

    private ConstructionEnum construction;

    private Integer yearOfManufacture;

    //todo why i need this on get?
    private List<MonthsPaidDTO> monthsPaid = new ArrayList<>();

    private Integer yearOfRenovation;

    private String propertyCode;

    private Boolean furnitured;

    private String furnituredDescription;

    private Boolean deleted;

    private LocationDTO address;

    private Set<byte[]> images;
    private Set<UserReviewDTO> reviews;

    public class MonthsPaidDTO {

        private boolean paid;
        private LocalDate datePaid;

        public boolean isPaid() {
            return paid;
        }

        public void setPaid(boolean paid) {
            this.paid = paid;
        }

        public LocalDate getDatePaid() {
            return datePaid;
        }

        public void setDatePaid(LocalDate datePaid) {
            this.datePaid = datePaid;
        }
    }

    public static class AvailabilityDto {

        private String currentlyRented;
        private LocalDate availableFrom;
        private LocalDate lastUpdate;

        public String getCurrentlyRented() {
            return currentlyRented;
        }

        public void setCurrentlyRented(String currentlyRented) {
            this.currentlyRented = currentlyRented;
        }

        public LocalDate getAvailableFrom() {
            return availableFrom;
        }

        public void setAvailableFrom(LocalDate availableFrom) {
            this.availableFrom = availableFrom;
        }

        public LocalDate getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(LocalDate lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTenantGuarantee() {
        return tenantGuarantee;
    }

    public void setTenantGuarantee(Long tenantGuarantee) {
        this.tenantGuarantee = tenantGuarantee;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocationDTO getAddress() {
        return address;
    }

    public void setAddress(LocationDTO address) {
        this.address = address;
    }

    public Set<byte[]> getImages() {
        return images;
    }

    public void setImages(Set<byte[]> images) {
        this.images = images;
    }

    public Set<UserReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(Set<UserReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public Set<HouseCharacteristicsDTO> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<HouseCharacteristicsDTO> characteristics) {
        this.characteristics = characteristics;
    }

    public AvailabilityDto getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityDto availability) {
        this.availability = availability;
    }

    public List<MonthsPaidDTO> getMonthsPaid() {
        return monthsPaid;
    }

    public void setMonthsPaid(List<MonthsPaidDTO> monthsPaid) {
        this.monthsPaid = monthsPaid;
    }
}
