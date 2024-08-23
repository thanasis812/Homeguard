package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.SubscriptionEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import com.hg.domain.enumeration.UserStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tenant.
 */
@Entity
@Table(name = "tenant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Max(value = 1)
    @Column(name = "gender")
    private Integer gender;

    @Column(name = "afm")
    private Integer afm;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})")
    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private UserCategoryEnum category;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatusEnum status;

    @Column(name = "settings_metadata")
    private String settingsMetadata;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private SubscriptionEnum subscriptionType;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "tenant", "property" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "tenant", "landLord", "property", "review" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Image tenantImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    @JsonIgnoreProperties(value = { "property", "tenant" }, allowSetters = true)
    private Set<TenantPropertyPreferences> propertyPreferences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    @JsonIgnoreProperties(value = { "images", "tenant", "landLord", "property" }, allowSetters = true)
    private Set<Review> apartmentReviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    @JsonIgnoreProperties(value = { "payments", "tenant", "propertyOwner", "property" }, allowSetters = true)
    private Set<RentalAgreement> rentedPropertysAgreements = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "user", "owner", "landLordImage", "propertys", "tenantReviews", "rentalAgreements" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "owner")
    private LandLord landLord;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tenant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Tenant firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Tenant lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGender() {
        return this.gender;
    }

    public Tenant gender(Integer gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAfm() {
        return this.afm;
    }

    public Tenant afm(Integer afm) {
        this.setAfm(afm);
        return this;
    }

    public void setAfm(Integer afm) {
        this.afm = afm;
    }

    public String getEmail() {
        return this.email;
    }

    public Tenant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Tenant phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserCategoryEnum getCategory() {
        return this.category;
    }

    public Tenant category(UserCategoryEnum category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(UserCategoryEnum category) {
        this.category = category;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Tenant createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public UserStatusEnum getStatus() {
        return this.status;
    }

    public Tenant status(UserStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    public String getSettingsMetadata() {
        return this.settingsMetadata;
    }

    public Tenant settingsMetadata(String settingsMetadata) {
        this.setSettingsMetadata(settingsMetadata);
        return this;
    }

    public void setSettingsMetadata(String settingsMetadata) {
        this.settingsMetadata = settingsMetadata;
    }

    public SubscriptionEnum getSubscriptionType() {
        return this.subscriptionType;
    }

    public Tenant subscriptionType(SubscriptionEnum subscriptionType) {
        this.setSubscriptionType(subscriptionType);
        return this;
    }

    public void setSubscriptionType(SubscriptionEnum subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Tenant deleted(Boolean deleted) {
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

    public Tenant user(User user) {
        this.setUser(user);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Tenant location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Image getTenantImage() {
        return this.tenantImage;
    }

    public void setTenantImage(Image image) {
        this.tenantImage = image;
    }

    public Tenant tenantImage(Image image) {
        this.setTenantImage(image);
        return this;
    }

    public Set<TenantPropertyPreferences> getPropertyPreferences() {
        return this.propertyPreferences;
    }

    public void setPropertyPreferences(Set<TenantPropertyPreferences> tenantPropertyPreferences) {
        if (this.propertyPreferences != null) {
            this.propertyPreferences.forEach(i -> i.setTenant(null));
        }
        if (tenantPropertyPreferences != null) {
            tenantPropertyPreferences.forEach(i -> i.setTenant(this));
        }
        this.propertyPreferences = tenantPropertyPreferences;
    }

    public Tenant propertyPreferences(Set<TenantPropertyPreferences> tenantPropertyPreferences) {
        this.setPropertyPreferences(tenantPropertyPreferences);
        return this;
    }

    public Tenant addPropertyPreferences(TenantPropertyPreferences tenantPropertyPreferences) {
        this.propertyPreferences.add(tenantPropertyPreferences);
        tenantPropertyPreferences.setTenant(this);
        return this;
    }

    public Tenant removePropertyPreferences(TenantPropertyPreferences tenantPropertyPreferences) {
        this.propertyPreferences.remove(tenantPropertyPreferences);
        tenantPropertyPreferences.setTenant(null);
        return this;
    }

    public Set<Review> getApartmentReviews() {
        return this.apartmentReviews;
    }

    public void setApartmentReviews(Set<Review> reviews) {
        if (this.apartmentReviews != null) {
            this.apartmentReviews.forEach(i -> i.setTenant(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setTenant(this));
        }
        this.apartmentReviews = reviews;
    }

    public Tenant apartmentReviews(Set<Review> reviews) {
        this.setApartmentReviews(reviews);
        return this;
    }

    public Tenant addApartmentReview(Review review) {
        this.apartmentReviews.add(review);
        review.setTenant(this);
        return this;
    }

    public Tenant removeApartmentReview(Review review) {
        this.apartmentReviews.remove(review);
        review.setTenant(null);
        return this;
    }

    public Set<RentalAgreement> getRentedPropertysAgreements() {
        return this.rentedPropertysAgreements;
    }

    public void setRentedPropertysAgreements(Set<RentalAgreement> rentalAgreements) {
        if (this.rentedPropertysAgreements != null) {
            this.rentedPropertysAgreements.forEach(i -> i.setTenant(null));
        }
        if (rentalAgreements != null) {
            rentalAgreements.forEach(i -> i.setTenant(this));
        }
        this.rentedPropertysAgreements = rentalAgreements;
    }

    public Tenant rentedPropertysAgreements(Set<RentalAgreement> rentalAgreements) {
        this.setRentedPropertysAgreements(rentalAgreements);
        return this;
    }

    public Tenant addRentedPropertysAgreement(RentalAgreement rentalAgreement) {
        this.rentedPropertysAgreements.add(rentalAgreement);
        rentalAgreement.setTenant(this);
        return this;
    }

    public Tenant removeRentedPropertysAgreement(RentalAgreement rentalAgreement) {
        this.rentedPropertysAgreements.remove(rentalAgreement);
        rentalAgreement.setTenant(null);
        return this;
    }

    public LandLord getLandLord() {
        return this.landLord;
    }

    public void setLandLord(LandLord landLord) {
        if (this.landLord != null) {
            this.landLord.setOwner(null);
        }
        if (landLord != null) {
            landLord.setOwner(this);
        }
        this.landLord = landLord;
    }

    public Tenant landLord(LandLord landLord) {
        this.setLandLord(landLord);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenant)) {
            return false;
        }
        return getId() != null && getId().equals(((Tenant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender=" + getGender() +
            ", afm=" + getAfm() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", category='" + getCategory() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", settingsMetadata='" + getSettingsMetadata() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
