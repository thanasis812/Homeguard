package com.hg.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PropertyCriteriaTest {

    @Test
    void newPropertyCriteriaHasAllFiltersNullTest() {
        var propertyCriteria = new PropertyCriteria();
        assertThat(propertyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void propertyCriteriaFluentMethodsCreatesFiltersTest() {
        var propertyCriteria = new PropertyCriteria();

        setAllFilters(propertyCriteria);

        assertThat(propertyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void propertyCriteriaCopyCreatesNullFilterTest() {
        var propertyCriteria = new PropertyCriteria();
        var copy = propertyCriteria.copy();

        assertThat(propertyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(propertyCriteria)
        );
    }

    @Test
    void propertyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var propertyCriteria = new PropertyCriteria();
        setAllFilters(propertyCriteria);

        var copy = propertyCriteria.copy();

        assertThat(propertyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(propertyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var propertyCriteria = new PropertyCriteria();

        assertThat(propertyCriteria).hasToString("PropertyCriteria{}");
    }

    private static void setAllFilters(PropertyCriteria propertyCriteria) {
        propertyCriteria.id();
        propertyCriteria.verified();
        propertyCriteria.name();
        propertyCriteria.description();
        propertyCriteria.price();
        propertyCriteria.squareMeters();
        propertyCriteria.plotSquareMeters();
        propertyCriteria.numberOfBathrooms();
        propertyCriteria.numberOfBedrooms();
        propertyCriteria.numberOfKitchens();
        propertyCriteria.numberOfAirConditioner();
        propertyCriteria.contractYears();
        propertyCriteria.nextAvailableDateForRent();
        propertyCriteria.thumbnail();
        propertyCriteria.houseType();
        propertyCriteria.floor();
        propertyCriteria.numberOfFlats();
        propertyCriteria.energyClass();
        propertyCriteria.construction();
        propertyCriteria.yearOfManufacture();
        propertyCriteria.yearOfRenovation();
        propertyCriteria.propertyCode();
        propertyCriteria.furnitured();
        propertyCriteria.furnituredDescription();
        propertyCriteria.deleted();
        propertyCriteria.locationId();
        propertyCriteria.rentalId();
        propertyCriteria.houseCharacteristicId();
        propertyCriteria.reviewsId();
        propertyCriteria.propertysPhotoId();
        propertyCriteria.tenantPropertyPreferencesId();
        propertyCriteria.distinct();
    }

    private static Condition<PropertyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getVerified()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getSquareMeters()) &&
                condition.apply(criteria.getPlotSquareMeters()) &&
                condition.apply(criteria.getNumberOfBathrooms()) &&
                condition.apply(criteria.getNumberOfBedrooms()) &&
                condition.apply(criteria.getNumberOfKitchens()) &&
                condition.apply(criteria.getNumberOfAirConditioner()) &&
                condition.apply(criteria.getContractYears()) &&
                condition.apply(criteria.getNextAvailableDateForRent()) &&
                condition.apply(criteria.getThumbnail()) &&
                condition.apply(criteria.getHouseType()) &&
                condition.apply(criteria.getFloor()) &&
                condition.apply(criteria.getNumberOfFlats()) &&
                condition.apply(criteria.getEnergyClass()) &&
                condition.apply(criteria.getConstruction()) &&
                condition.apply(criteria.getYearOfManufacture()) &&
                condition.apply(criteria.getYearOfRenovation()) &&
                condition.apply(criteria.getPropertyCode()) &&
                condition.apply(criteria.getFurnitured()) &&
                condition.apply(criteria.getFurnituredDescription()) &&
                condition.apply(criteria.getDeleted()) &&
                condition.apply(criteria.getLocationId()) &&
                condition.apply(criteria.getRentalId()) &&
                condition.apply(criteria.getHouseCharacteristicId()) &&
                condition.apply(criteria.getReviewsId()) &&
                condition.apply(criteria.getPropertysPhotoId()) &&
                condition.apply(criteria.getTenantPropertyPreferencesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PropertyCriteria> copyFiltersAre(PropertyCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getVerified(), copy.getVerified()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getSquareMeters(), copy.getSquareMeters()) &&
                condition.apply(criteria.getPlotSquareMeters(), copy.getPlotSquareMeters()) &&
                condition.apply(criteria.getNumberOfBathrooms(), copy.getNumberOfBathrooms()) &&
                condition.apply(criteria.getNumberOfBedrooms(), copy.getNumberOfBedrooms()) &&
                condition.apply(criteria.getNumberOfKitchens(), copy.getNumberOfKitchens()) &&
                condition.apply(criteria.getNumberOfAirConditioner(), copy.getNumberOfAirConditioner()) &&
                condition.apply(criteria.getContractYears(), copy.getContractYears()) &&
                condition.apply(criteria.getNextAvailableDateForRent(), copy.getNextAvailableDateForRent()) &&
                condition.apply(criteria.getThumbnail(), copy.getThumbnail()) &&
                condition.apply(criteria.getHouseType(), copy.getHouseType()) &&
                condition.apply(criteria.getFloor(), copy.getFloor()) &&
                condition.apply(criteria.getNumberOfFlats(), copy.getNumberOfFlats()) &&
                condition.apply(criteria.getEnergyClass(), copy.getEnergyClass()) &&
                condition.apply(criteria.getConstruction(), copy.getConstruction()) &&
                condition.apply(criteria.getYearOfManufacture(), copy.getYearOfManufacture()) &&
                condition.apply(criteria.getYearOfRenovation(), copy.getYearOfRenovation()) &&
                condition.apply(criteria.getPropertyCode(), copy.getPropertyCode()) &&
                condition.apply(criteria.getFurnitured(), copy.getFurnitured()) &&
                condition.apply(criteria.getFurnituredDescription(), copy.getFurnituredDescription()) &&
                condition.apply(criteria.getDeleted(), copy.getDeleted()) &&
                condition.apply(criteria.getLocationId(), copy.getLocationId()) &&
                condition.apply(criteria.getRentalId(), copy.getRentalId()) &&
                condition.apply(criteria.getHouseCharacteristicId(), copy.getHouseCharacteristicId()) &&
                condition.apply(criteria.getReviewsId(), copy.getReviewsId()) &&
                condition.apply(criteria.getPropertysPhotoId(), copy.getPropertysPhotoId()) &&
                condition.apply(criteria.getTenantPropertyPreferencesId(), copy.getTenantPropertyPreferencesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
