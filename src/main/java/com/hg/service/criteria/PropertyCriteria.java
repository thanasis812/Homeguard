package com.hg.service.criteria;

import com.hg.domain.enumeration.ConstructionEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.hg.domain.Property} entity. This class is used
 * in {@link com.hg.web.rest.PropertyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /properties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PropertyCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ConstructionEnum
     */
    public static class ConstructionEnumFilter extends Filter<ConstructionEnum> {

        public ConstructionEnumFilter() {}

        public ConstructionEnumFilter(ConstructionEnumFilter filter) {
            super(filter);
        }

        @Override
        public ConstructionEnumFilter copy() {
            return new ConstructionEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private BooleanFilter verified;

    private StringFilter name;

    private StringFilter description;

    private BigDecimalFilter price;

    private BigDecimalFilter squareMeters;

    private BigDecimalFilter plotSquareMeters;

    private IntegerFilter numberOfBathrooms;

    private IntegerFilter numberOfBedrooms;

    private IntegerFilter numberOfKitchens;

    private IntegerFilter numberOfAirConditioner;

    private IntegerFilter contractYears;

    private LocalDateFilter nextAvailableDateForRent;

    private StringFilter houseType;

    private IntegerFilter floor;

    private IntegerFilter numberOfFlats;

    private StringFilter energyClass;

    private ConstructionEnumFilter construction;

    private IntegerFilter yearOfManufacture;

    private IntegerFilter yearOfRenovation;

    private StringFilter propertyCode;

    private BooleanFilter furnitured;

    private StringFilter furnituredDescription;

    private BooleanFilter deleted;

    private LongFilter locationId;

    private Boolean distinct;

    public PropertyCriteria() {}

    public PropertyCriteria(PropertyCriteria other) {
        this.verified = other.optionalVerified().map(BooleanFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(BigDecimalFilter::copy).orElse(null);
        this.squareMeters = other.optionalSquareMeters().map(BigDecimalFilter::copy).orElse(null);
        this.plotSquareMeters = other.optionalPlotSquareMeters().map(BigDecimalFilter::copy).orElse(null);
        this.numberOfBathrooms = other.optionalNumberOfBathrooms().map(IntegerFilter::copy).orElse(null);
        this.numberOfBedrooms = other.optionalNumberOfBedrooms().map(IntegerFilter::copy).orElse(null);
        this.numberOfKitchens = other.optionalNumberOfKitchens().map(IntegerFilter::copy).orElse(null);
        this.numberOfAirConditioner = other.optionalNumberOfAirConditioner().map(IntegerFilter::copy).orElse(null);
        this.contractYears = other.optionalContractYears().map(IntegerFilter::copy).orElse(null);
        this.nextAvailableDateForRent = other.optionalNextAvailableDateForRent().map(LocalDateFilter::copy).orElse(null);
        this.houseType = other.optionalHouseType().map(StringFilter::copy).orElse(null);
        this.floor = other.optionalFloor().map(IntegerFilter::copy).orElse(null);
        this.numberOfFlats = other.optionalNumberOfFlats().map(IntegerFilter::copy).orElse(null);
        this.energyClass = other.optionalEnergyClass().map(StringFilter::copy).orElse(null);
        this.construction = other.optionalConstruction().map(ConstructionEnumFilter::copy).orElse(null);
        this.yearOfManufacture = other.optionalYearOfManufacture().map(IntegerFilter::copy).orElse(null);
        this.yearOfRenovation = other.optionalYearOfRenovation().map(IntegerFilter::copy).orElse(null);
        this.propertyCode = other.optionalPropertyCode().map(StringFilter::copy).orElse(null);
        this.furnitured = other.optionalFurnitured().map(BooleanFilter::copy).orElse(null);
        this.furnituredDescription = other.optionalFurnituredDescription().map(StringFilter::copy).orElse(null);
        this.deleted = other.optionalDeleted().map(BooleanFilter::copy).orElse(null);
        this.locationId = other.optionalLocationId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PropertyCriteria copy() {
        return new PropertyCriteria(this);
    }

    public BooleanFilter getVerified() {
        return verified;
    }

    public Optional<BooleanFilter> optionalVerified() {
        return Optional.ofNullable(verified);
    }

    public BooleanFilter verified() {
        if (verified == null) {
            setVerified(new BooleanFilter());
        }
        return verified;
    }

    public void setVerified(BooleanFilter verified) {
        this.verified = verified;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public Optional<BigDecimalFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public BigDecimalFilter price() {
        if (price == null) {
            setPrice(new BigDecimalFilter());
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public BigDecimalFilter getSquareMeters() {
        return squareMeters;
    }

    public Optional<BigDecimalFilter> optionalSquareMeters() {
        return Optional.ofNullable(squareMeters);
    }

    public BigDecimalFilter squareMeters() {
        if (squareMeters == null) {
            setSquareMeters(new BigDecimalFilter());
        }
        return squareMeters;
    }

    public void setSquareMeters(BigDecimalFilter squareMeters) {
        this.squareMeters = squareMeters;
    }

    public BigDecimalFilter getPlotSquareMeters() {
        return plotSquareMeters;
    }

    public Optional<BigDecimalFilter> optionalPlotSquareMeters() {
        return Optional.ofNullable(plotSquareMeters);
    }

    public BigDecimalFilter plotSquareMeters() {
        if (plotSquareMeters == null) {
            setPlotSquareMeters(new BigDecimalFilter());
        }
        return plotSquareMeters;
    }

    public void setPlotSquareMeters(BigDecimalFilter plotSquareMeters) {
        this.plotSquareMeters = plotSquareMeters;
    }

    public IntegerFilter getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public Optional<IntegerFilter> optionalNumberOfBathrooms() {
        return Optional.ofNullable(numberOfBathrooms);
    }

    public IntegerFilter numberOfBathrooms() {
        if (numberOfBathrooms == null) {
            setNumberOfBathrooms(new IntegerFilter());
        }
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(IntegerFilter numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public IntegerFilter getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public Optional<IntegerFilter> optionalNumberOfBedrooms() {
        return Optional.ofNullable(numberOfBedrooms);
    }

    public IntegerFilter numberOfBedrooms() {
        if (numberOfBedrooms == null) {
            setNumberOfBedrooms(new IntegerFilter());
        }
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(IntegerFilter numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public IntegerFilter getNumberOfKitchens() {
        return numberOfKitchens;
    }

    public Optional<IntegerFilter> optionalNumberOfKitchens() {
        return Optional.ofNullable(numberOfKitchens);
    }

    public IntegerFilter numberOfKitchens() {
        if (numberOfKitchens == null) {
            setNumberOfKitchens(new IntegerFilter());
        }
        return numberOfKitchens;
    }

    public void setNumberOfKitchens(IntegerFilter numberOfKitchens) {
        this.numberOfKitchens = numberOfKitchens;
    }

    public IntegerFilter getNumberOfAirConditioner() {
        return numberOfAirConditioner;
    }

    public Optional<IntegerFilter> optionalNumberOfAirConditioner() {
        return Optional.ofNullable(numberOfAirConditioner);
    }

    public IntegerFilter numberOfAirConditioner() {
        if (numberOfAirConditioner == null) {
            setNumberOfAirConditioner(new IntegerFilter());
        }
        return numberOfAirConditioner;
    }

    public void setNumberOfAirConditioner(IntegerFilter numberOfAirConditioner) {
        this.numberOfAirConditioner = numberOfAirConditioner;
    }

    public IntegerFilter getContractYears() {
        return contractYears;
    }

    public Optional<IntegerFilter> optionalContractYears() {
        return Optional.ofNullable(contractYears);
    }

    public IntegerFilter contractYears() {
        if (contractYears == null) {
            setContractYears(new IntegerFilter());
        }
        return contractYears;
    }

    public void setContractYears(IntegerFilter contractYears) {
        this.contractYears = contractYears;
    }

    public LocalDateFilter getNextAvailableDateForRent() {
        return nextAvailableDateForRent;
    }

    public Optional<LocalDateFilter> optionalNextAvailableDateForRent() {
        return Optional.ofNullable(nextAvailableDateForRent);
    }

    public LocalDateFilter nextAvailableDateForRent() {
        if (nextAvailableDateForRent == null) {
            setNextAvailableDateForRent(new LocalDateFilter());
        }
        return nextAvailableDateForRent;
    }

    public void setNextAvailableDateForRent(LocalDateFilter nextAvailableDateForRent) {
        this.nextAvailableDateForRent = nextAvailableDateForRent;
    }

    public StringFilter getHouseType() {
        return houseType;
    }

    public Optional<StringFilter> optionalHouseType() {
        return Optional.ofNullable(houseType);
    }

    public StringFilter houseType() {
        if (houseType == null) {
            setHouseType(new StringFilter());
        }
        return houseType;
    }

    public void setHouseType(StringFilter houseType) {
        this.houseType = houseType;
    }

    public IntegerFilter getFloor() {
        return floor;
    }

    public Optional<IntegerFilter> optionalFloor() {
        return Optional.ofNullable(floor);
    }

    public IntegerFilter floor() {
        if (floor == null) {
            setFloor(new IntegerFilter());
        }
        return floor;
    }

    public void setFloor(IntegerFilter floor) {
        this.floor = floor;
    }

    public IntegerFilter getNumberOfFlats() {
        return numberOfFlats;
    }

    public Optional<IntegerFilter> optionalNumberOfFlats() {
        return Optional.ofNullable(numberOfFlats);
    }

    public IntegerFilter numberOfFlats() {
        if (numberOfFlats == null) {
            setNumberOfFlats(new IntegerFilter());
        }
        return numberOfFlats;
    }

    public void setNumberOfFlats(IntegerFilter numberOfFlats) {
        this.numberOfFlats = numberOfFlats;
    }

    public StringFilter getEnergyClass() {
        return energyClass;
    }

    public Optional<StringFilter> optionalEnergyClass() {
        return Optional.ofNullable(energyClass);
    }

    public StringFilter energyClass() {
        if (energyClass == null) {
            setEnergyClass(new StringFilter());
        }
        return energyClass;
    }

    public void setEnergyClass(StringFilter energyClass) {
        this.energyClass = energyClass;
    }

    public ConstructionEnumFilter getConstruction() {
        return construction;
    }

    public Optional<ConstructionEnumFilter> optionalConstruction() {
        return Optional.ofNullable(construction);
    }

    public ConstructionEnumFilter construction() {
        if (construction == null) {
            setConstruction(new ConstructionEnumFilter());
        }
        return construction;
    }

    public void setConstruction(ConstructionEnumFilter construction) {
        this.construction = construction;
    }

    public IntegerFilter getYearOfManufacture() {
        return yearOfManufacture;
    }

    public Optional<IntegerFilter> optionalYearOfManufacture() {
        return Optional.ofNullable(yearOfManufacture);
    }

    public IntegerFilter yearOfManufacture() {
        if (yearOfManufacture == null) {
            setYearOfManufacture(new IntegerFilter());
        }
        return yearOfManufacture;
    }

    public void setYearOfManufacture(IntegerFilter yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public IntegerFilter getYearOfRenovation() {
        return yearOfRenovation;
    }

    public Optional<IntegerFilter> optionalYearOfRenovation() {
        return Optional.ofNullable(yearOfRenovation);
    }

    public IntegerFilter yearOfRenovation() {
        if (yearOfRenovation == null) {
            setYearOfRenovation(new IntegerFilter());
        }
        return yearOfRenovation;
    }

    public void setYearOfRenovation(IntegerFilter yearOfRenovation) {
        this.yearOfRenovation = yearOfRenovation;
    }

    public StringFilter getPropertyCode() {
        return propertyCode;
    }

    public Optional<StringFilter> optionalPropertyCode() {
        return Optional.ofNullable(propertyCode);
    }

    public StringFilter propertyCode() {
        if (propertyCode == null) {
            setPropertyCode(new StringFilter());
        }
        return propertyCode;
    }

    public void setPropertyCode(StringFilter propertyCode) {
        this.propertyCode = propertyCode;
    }

    public BooleanFilter getFurnitured() {
        return furnitured;
    }

    public Optional<BooleanFilter> optionalFurnitured() {
        return Optional.ofNullable(furnitured);
    }

    public BooleanFilter furnitured() {
        if (furnitured == null) {
            setFurnitured(new BooleanFilter());
        }
        return furnitured;
    }

    public void setFurnitured(BooleanFilter furnitured) {
        this.furnitured = furnitured;
    }

    public StringFilter getFurnituredDescription() {
        return furnituredDescription;
    }

    public Optional<StringFilter> optionalFurnituredDescription() {
        return Optional.ofNullable(furnituredDescription);
    }

    public StringFilter furnituredDescription() {
        if (furnituredDescription == null) {
            setFurnituredDescription(new StringFilter());
        }
        return furnituredDescription;
    }

    public void setFurnituredDescription(StringFilter furnituredDescription) {
        this.furnituredDescription = furnituredDescription;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public Optional<BooleanFilter> optionalDeleted() {
        return Optional.ofNullable(deleted);
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            setDeleted(new BooleanFilter());
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public Optional<LongFilter> optionalLocationId() {
        return Optional.ofNullable(locationId);
    }

    public LongFilter locationId() {
        if (locationId == null) {
            setLocationId(new LongFilter());
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropertyCriteria that = (PropertyCriteria) o;
        return (
            Objects.equals(verified, that.verified) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(price, that.price) &&
            Objects.equals(squareMeters, that.squareMeters) &&
            Objects.equals(plotSquareMeters, that.plotSquareMeters) &&
            Objects.equals(numberOfBathrooms, that.numberOfBathrooms) &&
            Objects.equals(numberOfBedrooms, that.numberOfBedrooms) &&
            Objects.equals(numberOfKitchens, that.numberOfKitchens) &&
            Objects.equals(numberOfAirConditioner, that.numberOfAirConditioner) &&
            Objects.equals(contractYears, that.contractYears) &&
            Objects.equals(nextAvailableDateForRent, that.nextAvailableDateForRent) &&
            Objects.equals(houseType, that.houseType) &&
            Objects.equals(floor, that.floor) &&
            Objects.equals(numberOfFlats, that.numberOfFlats) &&
            Objects.equals(energyClass, that.energyClass) &&
            Objects.equals(construction, that.construction) &&
            Objects.equals(yearOfManufacture, that.yearOfManufacture) &&
            Objects.equals(yearOfRenovation, that.yearOfRenovation) &&
            Objects.equals(propertyCode, that.propertyCode) &&
            Objects.equals(furnitured, that.furnitured) &&
            Objects.equals(furnituredDescription, that.furnituredDescription) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            verified,
            name,
            description,
            price,
            squareMeters,
            plotSquareMeters,
            numberOfBathrooms,
            numberOfBedrooms,
            numberOfKitchens,
            numberOfAirConditioner,
            contractYears,
            nextAvailableDateForRent,
            houseType,
            floor,
            numberOfFlats,
            energyClass,
            construction,
            yearOfManufacture,
            yearOfRenovation,
            propertyCode,
            furnitured,
            furnituredDescription,
            deleted,
            locationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PropertyCriteria{" +
            optionalVerified().map(f -> "verified=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalSquareMeters().map(f -> "squareMeters=" + f + ", ").orElse("") +
            optionalPlotSquareMeters().map(f -> "plotSquareMeters=" + f + ", ").orElse("") +
            optionalNumberOfBathrooms().map(f -> "numberOfBathrooms=" + f + ", ").orElse("") +
            optionalNumberOfBedrooms().map(f -> "numberOfBedrooms=" + f + ", ").orElse("") +
            optionalNumberOfKitchens().map(f -> "numberOfKitchens=" + f + ", ").orElse("") +
            optionalNumberOfAirConditioner().map(f -> "numberOfAirConditioner=" + f + ", ").orElse("") +
            optionalContractYears().map(f -> "contractYears=" + f + ", ").orElse("") +
            optionalNextAvailableDateForRent().map(f -> "nextAvailableDateForRent=" + f + ", ").orElse("") +
            optionalHouseType().map(f -> "houseType=" + f + ", ").orElse("") +
            optionalFloor().map(f -> "floor=" + f + ", ").orElse("") +
            optionalNumberOfFlats().map(f -> "numberOfFlats=" + f + ", ").orElse("") +
            optionalEnergyClass().map(f -> "energyClass=" + f + ", ").orElse("") +
            optionalConstruction().map(f -> "construction=" + f + ", ").orElse("") +
            optionalYearOfManufacture().map(f -> "yearOfManufacture=" + f + ", ").orElse("") +
            optionalYearOfRenovation().map(f -> "yearOfRenovation=" + f + ", ").orElse("") +
            optionalPropertyCode().map(f -> "propertyCode=" + f + ", ").orElse("") +
            optionalFurnitured().map(f -> "furnitured=" + f + ", ").orElse("") +
            optionalFurnituredDescription().map(f -> "furnituredDescription=" + f + ", ").orElse("") +
            optionalDeleted().map(f -> "deleted=" + f + ", ").orElse("") +
            optionalLocationId().map(f -> "locationId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
