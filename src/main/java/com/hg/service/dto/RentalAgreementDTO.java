package com.hg.service.dto;

import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.RentalAgreement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalAgreementDTO implements Serializable {

    private Long id;

    @Lob
    private String agreements;

    @Lob
    private String deliveryProtocol;

    @NotNull
    private Boolean tenantSign;

    private Boolean landLordSigned;

    @NotNull
    private RentalAgreementStatusEnum status;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private LocalDate createdDate;

    @NotNull
    private LocalDate latest;

    private Boolean deleted;

    private TenantDTO tenant;

    private LandLordDTO propertyOwner;

    private PropertyDTO property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreements() {
        return agreements;
    }

    public void setAgreements(String agreements) {
        this.agreements = agreements;
    }

    public String getDeliveryProtocol() {
        return deliveryProtocol;
    }

    public void setDeliveryProtocol(String deliveryProtocol) {
        this.deliveryProtocol = deliveryProtocol;
    }

    public Boolean getTenantSign() {
        return tenantSign;
    }

    public void setTenantSign(Boolean tenantSign) {
        this.tenantSign = tenantSign;
    }

    public Boolean getLandLordSigned() {
        return landLordSigned;
    }

    public void setLandLordSigned(Boolean landLordSigned) {
        this.landLordSigned = landLordSigned;
    }

    public RentalAgreementStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RentalAgreementStatusEnum status) {
        this.status = status;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLatest() {
        return latest;
    }

    public void setLatest(LocalDate latest) {
        this.latest = latest;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public LandLordDTO getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(LandLordDTO propertyOwner) {
        this.propertyOwner = propertyOwner;
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
        if (!(o instanceof RentalAgreementDTO)) {
            return false;
        }

        RentalAgreementDTO rentalAgreementDTO = (RentalAgreementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentalAgreementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalAgreementDTO{" +
            "id=" + getId() +
            ", agreements='" + getAgreements() + "'" +
            ", deliveryProtocol='" + getDeliveryProtocol() + "'" +
            ", tenantSign='" + getTenantSign() + "'" +
            ", landLordSigned='" + getLandLordSigned() + "'" +
            ", status='" + getStatus() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", latest='" + getLatest() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", tenant=" + getTenant() +
            ", propertyOwner=" + getPropertyOwner() +
            ", property=" + getProperty() +
            "}";
    }
}
