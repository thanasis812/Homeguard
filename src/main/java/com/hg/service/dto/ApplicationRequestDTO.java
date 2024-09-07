package com.hg.service.dto;

import com.hg.domain.enumeration.ApplicationRequestEnum;
import com.hg.domain.enumeration.TApplicationRequestEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.ApplicationRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationRequestDTO implements Serializable {

    private Long id;

    private String payload;

    @NotNull
    private TApplicationRequestEnum type;

    @NotNull
    private ApplicationRequestEnum status;

    @NotNull
    private LocalDate createdDate;

    private LocalDate updatedDate;

    private UserDTO user;

    private PropertyDTO property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public TApplicationRequestEnum getType() {
        return type;
    }

    public void setType(TApplicationRequestEnum type) {
        this.type = type;
    }

    public ApplicationRequestEnum getStatus() {
        return status;
    }

    public void setStatus(ApplicationRequestEnum status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof ApplicationRequestDTO)) {
            return false;
        }

        ApplicationRequestDTO applicationRequestDTO = (ApplicationRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationRequestDTO{" +
            "id=" + getId() +
            ", payload='" + getPayload() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", user=" + getUser() +
            ", property=" + getProperty() +
            "}";
    }
}
