package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.TenantStatusEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A LandLord.
 */
@Entity
@Table(name = "land_lord")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LandLord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private UserCategoryEnum category;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TenantStatusEnum status;

    @Column(name = "settings_metadata")
    private String settingsMetadata;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Tenant owner;

    @JsonIgnoreProperties(value = { "tenant", "landLord", "property", "review" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Image landLordImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "landLord")
    @JsonIgnoreProperties(
        value = {
            "location",
            "rentals",
            "houseCharacteristics",
            "reviews",
            "propertysPhotos",
            "applications",
            "landLord",
            "tenantPropertyPreferences",
        },
        allowSetters = true
    )
    private Set<Property> propertys = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "landLord")
    @JsonIgnoreProperties(value = { "images", "tenant", "landLord", "property" }, allowSetters = true)
    private Set<Review> tenantReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyOwner")
    @JsonIgnoreProperties(value = { "payments", "tenant", "propertyOwner", "property" }, allowSetters = true)
    private Set<RentalAgreement> rentalAgreements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LandLord id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCategoryEnum getCategory() {
        return this.category;
    }

    public LandLord category(UserCategoryEnum category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(UserCategoryEnum category) {
        this.category = category;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public LandLord createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public TenantStatusEnum getStatus() {
        return this.status;
    }

    public LandLord status(TenantStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TenantStatusEnum status) {
        this.status = status;
    }

    public String getSettingsMetadata() {
        return this.settingsMetadata;
    }

    public LandLord settingsMetadata(String settingsMetadata) {
        this.setSettingsMetadata(settingsMetadata);
        return this;
    }

    public void setSettingsMetadata(String settingsMetadata) {
        this.settingsMetadata = settingsMetadata;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public LandLord deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LandLord user(User user) {
        this.setUser(user);
        return this;
    }

    public Tenant getOwner() {
        return this.owner;
    }

    public void setOwner(Tenant tenant) {
        this.owner = tenant;
    }

    public LandLord owner(Tenant tenant) {
        this.setOwner(tenant);
        return this;
    }

    public Image getLandLordImage() {
        return this.landLordImage;
    }

    public void setLandLordImage(Image image) {
        this.landLordImage = image;
    }

    public LandLord landLordImage(Image image) {
        this.setLandLordImage(image);
        return this;
    }

    public Set<Property> getPropertys() {
        return this.propertys;
    }

    public void setPropertys(Set<Property> properties) {
        if (this.propertys != null) {
            this.propertys.forEach(i -> i.setLandLord(null));
        }
        if (properties != null) {
            properties.forEach(i -> i.setLandLord(this));
        }
        this.propertys = properties;
    }

    public LandLord propertys(Set<Property> properties) {
        this.setPropertys(properties);
        return this;
    }

    public LandLord addPropertys(Property property) {
        this.propertys.add(property);
        property.setLandLord(this);
        return this;
    }

    public LandLord removePropertys(Property property) {
        this.propertys.remove(property);
        property.setLandLord(null);
        return this;
    }

    public Set<Review> getTenantReviews() {
        return this.tenantReviews;
    }

    public void setTenantReviews(Set<Review> reviews) {
        if (this.tenantReviews != null) {
            this.tenantReviews.forEach(i -> i.setLandLord(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setLandLord(this));
        }
        this.tenantReviews = reviews;
    }

    public LandLord tenantReviews(Set<Review> reviews) {
        this.setTenantReviews(reviews);
        return this;
    }

    public LandLord addTenantReview(Review review) {
        this.tenantReviews.add(review);
        review.setLandLord(this);
        return this;
    }

    public LandLord removeTenantReview(Review review) {
        this.tenantReviews.remove(review);
        review.setLandLord(null);
        return this;
    }

    public Set<RentalAgreement> getRentalAgreements() {
        return this.rentalAgreements;
    }

    public void setRentalAgreements(Set<RentalAgreement> rentalAgreements) {
        if (this.rentalAgreements != null) {
            this.rentalAgreements.forEach(i -> i.setPropertyOwner(null));
        }
        if (rentalAgreements != null) {
            rentalAgreements.forEach(i -> i.setPropertyOwner(this));
        }
        this.rentalAgreements = rentalAgreements;
    }

    public LandLord rentalAgreements(Set<RentalAgreement> rentalAgreements) {
        this.setRentalAgreements(rentalAgreements);
        return this;
    }

    public LandLord addRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreements.add(rentalAgreement);
        rentalAgreement.setPropertyOwner(this);
        return this;
    }

    public LandLord removeRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreements.remove(rentalAgreement);
        rentalAgreement.setPropertyOwner(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandLord)) {
            return false;
        }
        return getId() != null && getId().equals(((LandLord) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandLord{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", settingsMetadata='" + getSettingsMetadata() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
