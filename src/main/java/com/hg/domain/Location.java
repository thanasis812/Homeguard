package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "apartment_unit")
    private String apartmentUnit;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state_province_region")
    private String stateProvinceRegion;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "local_geographic_division")
    private String localGeographicDivision;

    @Column(name = "municipal_community")
    private String municipalCommunity;

    @Lob
    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "number")
    private String number;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "directions")
    private String directions;

    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Tenant tenant;

    @JsonIgnoreProperties(
        value = { "location", "rentals", "houseCharacteristics", "reviews", "propertysPhotos", "tenantPropertyPreferences" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getApartmentUnit() {
        return this.apartmentUnit;
    }

    public Location apartmentUnit(String apartmentUnit) {
        this.setApartmentUnit(apartmentUnit);
        return this;
    }

    public void setApartmentUnit(String apartmentUnit) {
        this.apartmentUnit = apartmentUnit;
    }

    public String getCity() {
        return this.city;
    }

    public Location city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvinceRegion() {
        return this.stateProvinceRegion;
    }

    public Location stateProvinceRegion(String stateProvinceRegion) {
        this.setStateProvinceRegion(stateProvinceRegion);
        return this;
    }

    public void setStateProvinceRegion(String stateProvinceRegion) {
        this.stateProvinceRegion = stateProvinceRegion;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Location postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return this.country;
    }

    public Location country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Location latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Location longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocalGeographicDivision() {
        return this.localGeographicDivision;
    }

    public Location localGeographicDivision(String localGeographicDivision) {
        this.setLocalGeographicDivision(localGeographicDivision);
        return this;
    }

    public void setLocalGeographicDivision(String localGeographicDivision) {
        this.localGeographicDivision = localGeographicDivision;
    }

    public String getMunicipalCommunity() {
        return this.municipalCommunity;
    }

    public Location municipalCommunity(String municipalCommunity) {
        this.setMunicipalCommunity(municipalCommunity);
        return this;
    }

    public void setMunicipalCommunity(String municipalCommunity) {
        this.municipalCommunity = municipalCommunity;
    }

    public String getAdditionalNotes() {
        return this.additionalNotes;
    }

    public Location additionalNotes(String additionalNotes) {
        this.setAdditionalNotes(additionalNotes);
        return this;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getNumber() {
        return this.number;
    }

    public Location number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getZipCode() {
        return this.zipCode;
    }

    public Location zipCode(Integer zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getFloor() {
        return this.floor;
    }

    public Location floor(Integer floor) {
        this.setFloor(floor);
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getDirections() {
        return this.directions;
    }

    public Location directions(String directions) {
        this.setDirections(directions);
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        if (this.tenant != null) {
            this.tenant.setLocation(null);
        }
        if (tenant != null) {
            tenant.setLocation(this);
        }
        this.tenant = tenant;
    }

    public Location tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        if (this.property != null) {
            this.property.setLocation(null);
        }
        if (property != null) {
            property.setLocation(this);
        }
        this.property = property;
    }

    public Location property(Property property) {
        this.setProperty(property);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", apartmentUnit='" + getApartmentUnit() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvinceRegion='" + getStateProvinceRegion() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", localGeographicDivision='" + getLocalGeographicDivision() + "'" +
            ", municipalCommunity='" + getMunicipalCommunity() + "'" +
            ", additionalNotes='" + getAdditionalNotes() + "'" +
            ", number='" + getNumber() + "'" +
            ", zipCode=" + getZipCode() +
            ", floor=" + getFloor() +
            ", directions='" + getDirections() + "'" +
            "}";
    }
}
