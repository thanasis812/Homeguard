package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.ConstructionEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "square_meters", precision = 21, scale = 2)
    private BigDecimal squareMeters;

    @Column(name = "plot_square_meters", precision = 21, scale = 2)
    private BigDecimal plotSquareMeters;

    @Column(name = "number_of_bathrooms")
    private Integer numberOfBathrooms;

    @Column(name = "number_of_bedrooms")
    private Integer numberOfBedrooms;

    @Column(name = "number_of_kitchens")
    private Integer numberOfKitchens;

    @Column(name = "number_of_air_conditioner")
    private Integer numberOfAirConditioner;

    @Lob
    @Column(name = "house_rules")
    private String houseRules;

    @Column(name = "contract_years")
    private Integer contractYears;

    @Column(name = "next_available_date_for_rent")
    private LocalDate nextAvailableDateForRent;

    @Column(name = "thumbnail")
    private Long thumbnail;

    @Column(name = "house_type")
    private String houseType;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "number_of_flats")
    private Integer numberOfFlats;

    @Column(name = "energy_class")
    private String energyClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "construction")
    private ConstructionEnum construction;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @Column(name = "year_of_renovation")
    private Integer yearOfRenovation;

    @Column(name = "property_code")
    private String propertyCode;

    @Column(name = "furnitured")
    private Boolean furnitured;

    @Column(name = "furnitured_description")
    private String furnituredDescription;

    @Column(name = "deleted")
    private Boolean deleted;

    @JsonIgnoreProperties(value = { "tenant", "property" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonIgnoreProperties(value = { "payments", "tenant", "propertyOwner", "property" }, allowSetters = true)
    private Set<RentalAgreement> rentals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonIgnoreProperties(value = { "property" }, allowSetters = true)
    private Set<HouseCharacteristics> houseCharacteristics = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonIgnoreProperties(value = { "images", "tenant", "landLord", "property" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonIgnoreProperties(value = { "tenant", "landLord", "property", "review" }, allowSetters = true)
    private Set<Image> propertysPhotos = new HashSet<>();

    @JsonIgnoreProperties(value = { "property", "tenant" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "property")
    private TenantPropertyPreferences tenantPropertyPreferences;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Property id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVerified() {
        return this.verified;
    }

    public Property verified(Boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return this.name;
    }

    public Property name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Property description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Property price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSquareMeters() {
        return this.squareMeters;
    }

    public Property squareMeters(BigDecimal squareMeters) {
        this.setSquareMeters(squareMeters);
        return this;
    }

    public void setSquareMeters(BigDecimal squareMeters) {
        this.squareMeters = squareMeters;
    }

    public BigDecimal getPlotSquareMeters() {
        return this.plotSquareMeters;
    }

    public Property plotSquareMeters(BigDecimal plotSquareMeters) {
        this.setPlotSquareMeters(plotSquareMeters);
        return this;
    }

    public void setPlotSquareMeters(BigDecimal plotSquareMeters) {
        this.plotSquareMeters = plotSquareMeters;
    }

    public Integer getNumberOfBathrooms() {
        return this.numberOfBathrooms;
    }

    public Property numberOfBathrooms(Integer numberOfBathrooms) {
        this.setNumberOfBathrooms(numberOfBathrooms);
        return this;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Integer getNumberOfBedrooms() {
        return this.numberOfBedrooms;
    }

    public Property numberOfBedrooms(Integer numberOfBedrooms) {
        this.setNumberOfBedrooms(numberOfBedrooms);
        return this;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Integer getNumberOfKitchens() {
        return this.numberOfKitchens;
    }

    public Property numberOfKitchens(Integer numberOfKitchens) {
        this.setNumberOfKitchens(numberOfKitchens);
        return this;
    }

    public void setNumberOfKitchens(Integer numberOfKitchens) {
        this.numberOfKitchens = numberOfKitchens;
    }

    public Integer getNumberOfAirConditioner() {
        return this.numberOfAirConditioner;
    }

    public Property numberOfAirConditioner(Integer numberOfAirConditioner) {
        this.setNumberOfAirConditioner(numberOfAirConditioner);
        return this;
    }

    public void setNumberOfAirConditioner(Integer numberOfAirConditioner) {
        this.numberOfAirConditioner = numberOfAirConditioner;
    }

    public String getHouseRules() {
        return this.houseRules;
    }

    public Property houseRules(String houseRules) {
        this.setHouseRules(houseRules);
        return this;
    }

    public void setHouseRules(String houseRules) {
        this.houseRules = houseRules;
    }

    public Integer getContractYears() {
        return this.contractYears;
    }

    public Property contractYears(Integer contractYears) {
        this.setContractYears(contractYears);
        return this;
    }

    public void setContractYears(Integer contractYears) {
        this.contractYears = contractYears;
    }

    public LocalDate getNextAvailableDateForRent() {
        return this.nextAvailableDateForRent;
    }

    public Property nextAvailableDateForRent(LocalDate nextAvailableDateForRent) {
        this.setNextAvailableDateForRent(nextAvailableDateForRent);
        return this;
    }

    public void setNextAvailableDateForRent(LocalDate nextAvailableDateForRent) {
        this.nextAvailableDateForRent = nextAvailableDateForRent;
    }

    public Long getThumbnail() {
        return this.thumbnail;
    }

    public Property thumbnail(Long thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(Long thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getHouseType() {
        return this.houseType;
    }

    public Property houseType(String houseType) {
        this.setHouseType(houseType);
        return this;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public Property floor(Integer floor) {
        this.setFloor(floor);
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getNumberOfFlats() {
        return this.numberOfFlats;
    }

    public Property numberOfFlats(Integer numberOfFlats) {
        this.setNumberOfFlats(numberOfFlats);
        return this;
    }

    public void setNumberOfFlats(Integer numberOfFlats) {
        this.numberOfFlats = numberOfFlats;
    }

    public String getEnergyClass() {
        return this.energyClass;
    }

    public Property energyClass(String energyClass) {
        this.setEnergyClass(energyClass);
        return this;
    }

    public void setEnergyClass(String energyClass) {
        this.energyClass = energyClass;
    }

    public ConstructionEnum getConstruction() {
        return this.construction;
    }

    public Property construction(ConstructionEnum construction) {
        this.setConstruction(construction);
        return this;
    }

    public void setConstruction(ConstructionEnum construction) {
        this.construction = construction;
    }

    public Integer getYearOfManufacture() {
        return this.yearOfManufacture;
    }

    public Property yearOfManufacture(Integer yearOfManufacture) {
        this.setYearOfManufacture(yearOfManufacture);
        return this;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Integer getYearOfRenovation() {
        return this.yearOfRenovation;
    }

    public Property yearOfRenovation(Integer yearOfRenovation) {
        this.setYearOfRenovation(yearOfRenovation);
        return this;
    }

    public void setYearOfRenovation(Integer yearOfRenovation) {
        this.yearOfRenovation = yearOfRenovation;
    }

    public String getPropertyCode() {
        return this.propertyCode;
    }

    public Property propertyCode(String propertyCode) {
        this.setPropertyCode(propertyCode);
        return this;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public Boolean getFurnitured() {
        return this.furnitured;
    }

    public Property furnitured(Boolean furnitured) {
        this.setFurnitured(furnitured);
        return this;
    }

    public void setFurnitured(Boolean furnitured) {
        this.furnitured = furnitured;
    }

    public String getFurnituredDescription() {
        return this.furnituredDescription;
    }

    public Property furnituredDescription(String furnituredDescription) {
        this.setFurnituredDescription(furnituredDescription);
        return this;
    }

    public void setFurnituredDescription(String furnituredDescription) {
        this.furnituredDescription = furnituredDescription;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Property deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Property location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Set<RentalAgreement> getRentals() {
        return this.rentals;
    }

    public void setRentals(Set<RentalAgreement> rentalAgreements) {
        if (this.rentals != null) {
            this.rentals.forEach(i -> i.setProperty(null));
        }
        if (rentalAgreements != null) {
            rentalAgreements.forEach(i -> i.setProperty(this));
        }
        this.rentals = rentalAgreements;
    }

    public Property rentals(Set<RentalAgreement> rentalAgreements) {
        this.setRentals(rentalAgreements);
        return this;
    }

    public Property addRental(RentalAgreement rentalAgreement) {
        this.rentals.add(rentalAgreement);
        rentalAgreement.setProperty(this);
        return this;
    }

    public Property removeRental(RentalAgreement rentalAgreement) {
        this.rentals.remove(rentalAgreement);
        rentalAgreement.setProperty(null);
        return this;
    }

    public Set<HouseCharacteristics> getHouseCharacteristics() {
        return this.houseCharacteristics;
    }

    public void setHouseCharacteristics(Set<HouseCharacteristics> houseCharacteristics) {
        if (this.houseCharacteristics != null) {
            this.houseCharacteristics.forEach(i -> i.setProperty(null));
        }
        if (houseCharacteristics != null) {
            houseCharacteristics.forEach(i -> i.setProperty(this));
        }
        this.houseCharacteristics = houseCharacteristics;
    }

    public Property houseCharacteristics(Set<HouseCharacteristics> houseCharacteristics) {
        this.setHouseCharacteristics(houseCharacteristics);
        return this;
    }

    public Property addHouseCharacteristic(HouseCharacteristics houseCharacteristics) {
        this.houseCharacteristics.add(houseCharacteristics);
        houseCharacteristics.setProperty(this);
        return this;
    }

    public Property removeHouseCharacteristic(HouseCharacteristics houseCharacteristics) {
        this.houseCharacteristics.remove(houseCharacteristics);
        houseCharacteristics.setProperty(null);
        return this;
    }

    public Set<Review> getReviews() {
        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setProperty(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setProperty(this));
        }
        this.reviews = reviews;
    }

    public Property reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public Property addReviews(Review review) {
        this.reviews.add(review);
        review.setProperty(this);
        return this;
    }

    public Property removeReviews(Review review) {
        this.reviews.remove(review);
        review.setProperty(null);
        return this;
    }

    public Set<Image> getPropertysPhotos() {
        return this.propertysPhotos;
    }

    public void setPropertysPhotos(Set<Image> images) {
        if (this.propertysPhotos != null) {
            this.propertysPhotos.forEach(i -> i.setProperty(null));
        }
        if (images != null) {
            images.forEach(i -> i.setProperty(this));
        }
        this.propertysPhotos = images;
    }

    public Property propertysPhotos(Set<Image> images) {
        this.setPropertysPhotos(images);
        return this;
    }

    public Property addPropertysPhoto(Image image) {
        this.propertysPhotos.add(image);
        image.setProperty(this);
        return this;
    }

    public Property removePropertysPhoto(Image image) {
        this.propertysPhotos.remove(image);
        image.setProperty(null);
        return this;
    }

    public TenantPropertyPreferences getTenantPropertyPreferences() {
        return this.tenantPropertyPreferences;
    }

    public void setTenantPropertyPreferences(TenantPropertyPreferences tenantPropertyPreferences) {
        if (this.tenantPropertyPreferences != null) {
            this.tenantPropertyPreferences.setProperty(null);
        }
        if (tenantPropertyPreferences != null) {
            tenantPropertyPreferences.setProperty(this);
        }
        this.tenantPropertyPreferences = tenantPropertyPreferences;
    }

    public Property tenantPropertyPreferences(TenantPropertyPreferences tenantPropertyPreferences) {
        this.setTenantPropertyPreferences(tenantPropertyPreferences);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return getId() != null && getId().equals(((Property) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
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
            "}";
    }
}
