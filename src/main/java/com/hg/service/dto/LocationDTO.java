package com.hg.service.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Location} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String streetAddress;

    private String apartmentUnit;

    @NotNull
    private String city;

    private String stateProvinceRegion;

    @NotNull
    private String postalCode;

    @NotNull
    private String country;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private String localGeographicDivision;

    private String municipalCommunity;

    private String additionalNotes;

    private String number;

    @NotNull
    private Integer zipCode;

    private Integer floor;

    private String directions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getApartmentUnit() {
        return apartmentUnit;
    }

    public void setApartmentUnit(String apartmentUnit) {
        this.apartmentUnit = apartmentUnit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvinceRegion() {
        return stateProvinceRegion;
    }

    public void setStateProvinceRegion(String stateProvinceRegion) {
        this.stateProvinceRegion = stateProvinceRegion;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocalGeographicDivision() {
        return localGeographicDivision;
    }

    public void setLocalGeographicDivision(String localGeographicDivision) {
        this.localGeographicDivision = localGeographicDivision;
    }

    public String getMunicipalCommunity() {
        return municipalCommunity;
    }

    public void setMunicipalCommunity(String municipalCommunity) {
        this.municipalCommunity = municipalCommunity;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationDTO)) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationDTO{" +
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
