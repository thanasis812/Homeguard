package com.hg.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RentalAgreementAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAgreementAllPropertiesEquals(RentalAgreement expected, RentalAgreement actual) {
        assertRentalAgreementAutoGeneratedPropertiesEquals(expected, actual);
        assertRentalAgreementAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAgreementAllUpdatablePropertiesEquals(RentalAgreement expected, RentalAgreement actual) {
        assertRentalAgreementUpdatableFieldsEquals(expected, actual);
        assertRentalAgreementUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAgreementAutoGeneratedPropertiesEquals(RentalAgreement expected, RentalAgreement actual) {
        assertThat(expected)
            .as("Verify RentalAgreement auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAgreementUpdatableFieldsEquals(RentalAgreement expected, RentalAgreement actual) {
        assertThat(expected)
            .as("Verify RentalAgreement relevant properties")
            .satisfies(e -> assertThat(e.getAgreements()).as("check agreements").isEqualTo(actual.getAgreements()))
            .satisfies(e -> assertThat(e.getDeliveryProtocol()).as("check deliveryProtocol").isEqualTo(actual.getDeliveryProtocol()))
            .satisfies(e -> assertThat(e.getTenantSign()).as("check tenantSign").isEqualTo(actual.getTenantSign()))
            .satisfies(e -> assertThat(e.getLandLordSigned()).as("check landLordSigned").isEqualTo(actual.getLandLordSigned()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getExpirationDate()).as("check expirationDate").isEqualTo(actual.getExpirationDate()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()))
            .satisfies(e -> assertThat(e.getLatest()).as("check latest").isEqualTo(actual.getLatest()))
            .satisfies(e -> assertThat(e.getDeleted()).as("check deleted").isEqualTo(actual.getDeleted()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAgreementUpdatableRelationshipsEquals(RentalAgreement expected, RentalAgreement actual) {
        assertThat(expected)
            .as("Verify RentalAgreement relationships")
            .satisfies(e -> assertThat(e.getTenant()).as("check tenant").isEqualTo(actual.getTenant()))
            .satisfies(e -> assertThat(e.getPropertyOwner()).as("check propertyOwner").isEqualTo(actual.getPropertyOwner()))
            .satisfies(e -> assertThat(e.getProperty()).as("check property").isEqualTo(actual.getProperty()));
    }
}
