package com.hg.service.dto;

import com.hg.domain.enumeration.HouseCharacteristicsEnum;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.HouseCharacteristics} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HouseCharacteristicsDTO implements Serializable {

    private Long id;

    @NotNull
    private HouseCharacteristicsEnum code;

    private HouseCharacteristicsGroupEnum group;

    private Boolean primaryToUser;

    private Boolean deleted;

    private PropertyDTO property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HouseCharacteristicsEnum getCode() {
        return code;
    }

    public void setCode(HouseCharacteristicsEnum code) {
        this.code = code;
    }

    public HouseCharacteristicsGroupEnum getGroup() {
        return group;
    }

    public void setGroup(HouseCharacteristicsGroupEnum group) {
        this.group = group;
    }

    public Boolean getPrimaryToUser() {
        return primaryToUser;
    }

    public void setPrimaryToUser(Boolean primaryToUser) {
        this.primaryToUser = primaryToUser;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseCharacteristicsDTO)) {
            return false;
        }

        HouseCharacteristicsDTO houseCharacteristicsDTO = (HouseCharacteristicsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, houseCharacteristicsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseCharacteristicsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", group='" + getGroup() + "'" +
            ", primaryToUser='" + getPrimaryToUser() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", property=" + getProperty() +
            "}";
    }
}
