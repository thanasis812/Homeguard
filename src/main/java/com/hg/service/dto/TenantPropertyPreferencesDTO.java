package com.hg.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.TenantPropertyPreferences} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantPropertyPreferencesDTO implements Serializable {

    private Long id;

    private Boolean favorite;

    private LocalDate favoriteDate;

    private Boolean reminder;

    private LocalDate reminderDate;

    private Boolean deleted;

    private PropertyDTO property;

    private TenantDTO tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public LocalDate getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(LocalDate favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantPropertyPreferencesDTO)) {
            return false;
        }

        TenantPropertyPreferencesDTO tenantPropertyPreferencesDTO = (TenantPropertyPreferencesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tenantPropertyPreferencesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantPropertyPreferencesDTO{" +
            "id=" + getId() +
            ", favorite='" + getFavorite() + "'" +
            ", favoriteDate='" + getFavoriteDate() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", reminderDate='" + getReminderDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", property=" + getProperty() +
            ", tenant=" + getTenant() +
            "}";
    }
}
