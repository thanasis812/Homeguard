package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TenantPropertyPreferences.
 */
@Entity
@Table(name = "tenant_property_preferences")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantPropertyPreferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "favorite")
    private Boolean favorite;

    @Column(name = "favorite_date")
    private LocalDate favoriteDate;

    @Column(name = "reminder")
    private Boolean reminder;

    @Column(name = "reminder_date")
    private LocalDate reminderDate;

    @Column(name = "deleted")
    private Boolean deleted;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    private Tenant tenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TenantPropertyPreferences id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public TenantPropertyPreferences favorite(Boolean favorite) {
        this.setFavorite(favorite);
        return this;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public LocalDate getFavoriteDate() {
        return this.favoriteDate;
    }

    public TenantPropertyPreferences favoriteDate(LocalDate favoriteDate) {
        this.setFavoriteDate(favoriteDate);
        return this;
    }

    public void setFavoriteDate(LocalDate favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public Boolean getReminder() {
        return this.reminder;
    }

    public TenantPropertyPreferences reminder(Boolean reminder) {
        this.setReminder(reminder);
        return this;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    public LocalDate getReminderDate() {
        return this.reminderDate;
    }

    public TenantPropertyPreferences reminderDate(LocalDate reminderDate) {
        this.setReminderDate(reminderDate);
        return this;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public TenantPropertyPreferences deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public TenantPropertyPreferences property(Property property) {
        this.setProperty(property);
        return this;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public TenantPropertyPreferences tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantPropertyPreferences)) {
            return false;
        }
        return getId() != null && getId().equals(((TenantPropertyPreferences) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantPropertyPreferences{" +
            "id=" + getId() +
            ", favorite='" + getFavorite() + "'" +
            ", favoriteDate='" + getFavoriteDate() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", reminderDate='" + getReminderDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
