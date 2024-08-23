package com.hg.service.dto;

import com.hg.domain.enumeration.SubscriptionEnum;
import com.hg.domain.enumeration.UserCategoryEnum;
import com.hg.domain.enumeration.UserStatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Tenant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Max(value = 1)
    private Integer gender;

    private Integer afm;

    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    private String email;

    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})")
    private String phone;

    private UserCategoryEnum category;

    private LocalDate createdDate;

    private UserStatusEnum status;

    private String settingsMetadata;

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

    public Integer getAfm() {
        return afm;
    }

    public void setAfm(Integer afm) {
        this.afm = afm;
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
            ", user=" + getUser() +
            ", location=" + getLocation() +
            ", tenantImage=" + getTenantImage() +
            "}";
    }
}
