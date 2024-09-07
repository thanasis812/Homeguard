package com.hg.service.dto;

import com.hg.domain.enumeration.TenantStatusEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.LandLord} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LandLordDTO implements Serializable {

    private Long id;

    private UserCategoryEnum category;

    private LocalDate createdDate;

    @NotNull
    private TenantStatusEnum status;

    private String settingsMetadata;

    private Boolean deleted;

    private UserDTO user;

    private TenantDTO owner;

    private ImageDTO landLordImage;

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
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

    public TenantDTO getOwner() {
        return owner;
    }

    public void setOwner(TenantDTO owner) {
        this.owner = owner;
    }

    public ImageDTO getLandLordImage() {
        return landLordImage;
    }

    public void setLandLordImage(ImageDTO landLordImage) {
        this.landLordImage = landLordImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandLordDTO)) {
            return false;
        }

        LandLordDTO landLordDTO = (LandLordDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, landLordDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandLordDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", settingsMetadata='" + getSettingsMetadata() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", user=" + getUser() +
            ", owner=" + getOwner() +
            ", landLordImage=" + getLandLordImage() +
            "}";
    }
}
