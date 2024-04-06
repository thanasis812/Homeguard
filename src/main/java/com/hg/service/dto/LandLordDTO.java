package com.hg.service.dto;

import com.hg.domain.enumeration.UserCategoryEnum;
import com.hg.domain.enumeration.UserStatusEnum;
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

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Max(value = 1)
    private Integer gender;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    private String email;

    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})")
    private String phone;

    private UserCategoryEnum category;

    private LocalDate createdDate;

    @NotNull
    private UserStatusEnum status;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
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
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender=" + getGender() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
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
