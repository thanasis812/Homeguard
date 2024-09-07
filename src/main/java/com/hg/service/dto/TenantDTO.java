package com.hg.service.dto;

import com.hg.domain.enumeration.SubscriptionEnum;
import com.hg.domain.enumeration.TenantStatusEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Tenant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantDTO implements Serializable {

    private Long id;

    @NotNull
    private UserCategoryEnum category;

    @NotNull
    private TenantStatusEnum status;

    private String settingsMetadata;

    @NotNull
    private SubscriptionEnum subscriptionType;

    private Boolean deleted;

    private UserDTO user;

    private LocationDTO location;

    private ImageDTO tenantImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(UserCategoryEnum category) {
        this.category = category;
    }

    public TenantStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TenantStatusEnum status) {
        this.status = status;
    }

    public String getSettingsMetadata() {
        return settingsMetadata;
    }

    public void setSettingsMetadata(String settingsMetadata) {
        this.settingsMetadata = settingsMetadata;
    }

    public SubscriptionEnum getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionEnum subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public ImageDTO getTenantImage() {
        return tenantImage;
    }

    public void setTenantImage(ImageDTO tenantImage) {
        this.tenantImage = tenantImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantDTO)) {
            return false;
        }

        TenantDTO tenantDTO = (TenantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tenantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", status='" + getStatus() + "'" +
            ", settingsMetadata='" + getSettingsMetadata() + "'" +
            ", subscriptionType='" + getSubscriptionType() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", user=" + getUser() +
            ", location=" + getLocation() +
            ", tenantImage=" + getTenantImage() +
            "}";
    }
}
