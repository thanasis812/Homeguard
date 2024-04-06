package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Lob
    @Column(name = "updated_date")
    private byte[] updatedDate;

    @Column(name = "updated_date_content_type")
    private String updatedDateContentType;

    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tenantImage")
    private Tenant tenant;

    @JsonIgnoreProperties(value = { "user", "owner", "landLordImage", "tenantReviews", "rentalAgreements" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "landLordImage")
    private LandLord landLord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "location", "rentals", "houseCharacteristics", "reviews", "propertysPhotos", "tenantPropertyPreferences" },
        allowSetters = true
    )
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "images", "tenant", "landLord", "property" }, allowSetters = true)
    private Review review;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Image id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public Image path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Image createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getUpdatedDate() {
        return this.updatedDate;
    }

    public Image updatedDate(byte[] updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(byte[] updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDateContentType() {
        return this.updatedDateContentType;
    }

    public Image updatedDateContentType(String updatedDateContentType) {
        this.updatedDateContentType = updatedDateContentType;
        return this;
    }

    public void setUpdatedDateContentType(String updatedDateContentType) {
        this.updatedDateContentType = updatedDateContentType;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        if (this.tenant != null) {
            this.tenant.setTenantImage(null);
        }
        if (tenant != null) {
            tenant.setTenantImage(this);
        }
        this.tenant = tenant;
    }

    public Image tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public LandLord getLandLord() {
        return this.landLord;
    }

    public void setLandLord(LandLord landLord) {
        if (this.landLord != null) {
            this.landLord.setLandLordImage(null);
        }
        if (landLord != null) {
            landLord.setLandLordImage(this);
        }
        this.landLord = landLord;
    }

    public Image landLord(LandLord landLord) {
        this.setLandLord(landLord);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Image property(Property property) {
        this.setProperty(property);
        return this;
    }

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Image review(Review review) {
        this.setReview(review);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return getId() != null && getId().equals(((Image) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedDateContentType='" + getUpdatedDateContentType() + "'" +
            "}";
    }
}
