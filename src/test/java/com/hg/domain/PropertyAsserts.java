package com.hg.domain;

import static com.hg.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertyAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAllPropertiesEquals(Property expected, Property actual) {
        assertPropertyAutoGeneratedPropertiesEquals(expected, actual);
        assertPropertyAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAllUpdatablePropertiesEquals(Property expected, Property actual) {
        assertPropertyUpdatableFieldsEquals(expected, actual);
        assertPropertyUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAutoGeneratedPropertiesEquals(Property expected, Property actual) {
        assertThat(expected)
            .as("Verify Property auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyUpdatableFieldsEquals(Property expected, Property actual) {
        assertThat(expected)
            .as("Verify Property relevant properties")
            .satisfies(e -> assertThat(e.getVerified()).as("check verified").isEqualTo(actual.getVerified()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrice()))
            .satisfies(
                e ->
                    assertThat(e.getSquareMeters())
                        .as("check squareMeters")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getSquareMeters())
            )
            .satisfies(
                e ->
                    assertThat(e.getPlotSquareMeters())
                        .as("check plotSquareMeters")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getPlotSquareMeters())
            )
            .satisfies(e -> assertThat(e.getNumberOfBathrooms()).as("check numberOfBathrooms").isEqualTo(actual.getNumberOfBathrooms()))
            .satisfies(e -> assertThat(e.getNumberOfBedrooms()).as("check numberOfBedrooms").isEqualTo(actual.getNumberOfBedrooms()))
            .satisfies(e -> assertThat(e.getNumberOfKitchens()).as("check numberOfKitchens").isEqualTo(actual.getNumberOfKitchens()))
            .satisfies(
                e ->
                    assertThat(e.getNumberOfAirConditioner())
                        .as("check numberOfAirConditioner")
                        .isEqualTo(actual.getNumberOfAirConditioner())
            )
            .satisfies(e -> assertThat(e.getHouseRules()).as("check houseRules").isEqualTo(actual.getHouseRules()))
            .satisfies(e -> assertThat(e.getContractYears()).as("check contractYears").isEqualTo(actual.getContractYears()))
            .satisfies(
                e ->
                    assertThat(e.getNextAvailableDateForRent())
                        .as("check nextAvailableDateForRent")
                        .isEqualTo(actual.getNextAvailableDateForRent())
            )
            .satisfies(e -> assertThat(e.getThumbnail()).as("check thumbnail").isEqualTo(actual.getThumbnail()))
            .satisfies(e -> assertThat(e.getHouseType()).as("check houseType").isEqualTo(actual.getHouseType()))
            .satisfies(e -> assertThat(e.getFloor()).as("check floor").isEqualTo(actual.getFloor()))
            .satisfies(e -> assertThat(e.getNumberOfFlats()).as("check numberOfFlats").isEqualTo(actual.getNumberOfFlats()))
            .satisfies(e -> assertThat(e.getEnergyClass()).as("check energyClass").isEqualTo(actual.getEnergyClass()))
            .satisfies(e -> assertThat(e.getConstruction()).as("check construction").isEqualTo(actual.getConstruction()))
            .satisfies(e -> assertThat(e.getYearOfManufacture()).as("check yearOfManufacture").isEqualTo(actual.getYearOfManufacture()))
            .satisfies(e -> assertThat(e.getYearOfRenovation()).as("check yearOfRenovation").isEqualTo(actual.getYearOfRenovation()))
            .satisfies(e -> assertThat(e.getPropertyCode()).as("check propertyCode").isEqualTo(actual.getPropertyCode()))
            .satisfies(e -> assertThat(e.getFurnitured()).as("check furnitured").isEqualTo(actual.getFurnitured()))
            .satisfies(
                e -> assertThat(e.getFurnituredDescription()).as("check furnituredDescription").isEqualTo(actual.getFurnituredDescription())
            )
            .satisfies(e -> assertThat(e.getDeleted()).as("check deleted").isEqualTo(actual.getDeleted()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyUpdatableRelationshipsEquals(Property expected, Property actual) {
        assertThat(expected)
            .as("Verify Property relationships")
            .satisfies(e -> assertThat(e.getLocation()).as("check location").isEqualTo(actual.getLocation()))
            .satisfies(e -> assertThat(e.getLandLord()).as("check landLord").isEqualTo(actual.getLandLord()));
    }
}
