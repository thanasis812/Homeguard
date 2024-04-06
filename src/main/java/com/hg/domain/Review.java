package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "stars", nullable = false)
    private Integer stars;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
    @JsonIgnoreProperties(value = { "tenant", "landLord", "property", "review" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "owner", "landLordImage", "tenantReviews", "rentalAgreements" }, allowSetters = true)
    private LandLord landLord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "location", "rentals", "houseCharacteristics", "reviews", "propertysPhotos", "tenantPropertyPreferences" },
        allowSetters = true
    )
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Review id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Review description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return this.stars;
    }

    public Review stars(Integer stars) {
        this.setStars(stars);
        return this;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Review createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public Review updatedDate(LocalDate updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Review deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setReview(null));
        }
        if (images != null) {
            images.forEach(i -> i.setReview(this));
        }
        this.images = images;
    }

    public Review images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Review addImages(Image image) {
        this.images.add(image);
        image.setReview(this);
        return this;
    }

    public Review removeImages(Image image) {
        this.images.remove(image);
        image.setReview(null);
        return this;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Review tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public LandLord getLandLord() {
        return this.landLord;
    }

    public void setLandLord(LandLord landLord) {
        this.landLord = landLord;
    }

    public Review landLord(LandLord landLord) {
        this.setLandLord(landLord);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Review property(Property property) {
        this.setProperty(property);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return getId() != null && getId().equals(((Review) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", stars=" + getStars() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
