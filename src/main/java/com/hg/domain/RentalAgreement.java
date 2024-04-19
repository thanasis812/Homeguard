package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.RentalAgreementStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A RentalAgreement.
 */
@Entity
@Table(name = "rental_agreement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "agreements", nullable = false, columnDefinition = "TEXT")
    private String agreements;

    @Column(name = "delivery_protocol")
    private String deliveryProtocol;

    @NotNull
    @Column(name = "tenant_sign", nullable = false)
    private Boolean tenantSign;

    @Column(name = "land_lord_signed")
    private Boolean landLordSigned;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RentalAgreementStatusEnum status;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @NotNull
    @Column(name = "latest", nullable = false)
    private LocalDate latest;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentalAgreement")
    @JsonIgnoreProperties(value = { "rentalAgreement" }, allowSetters = true)
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "user", "location", "tenantImage", "propertyPreferences", "apartmentReviews", "rentedPropertysAgreements", "landLord" },
        allowSetters = true
    )
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "owner", "landLordImage", "tenantReviews", "rentalAgreements" }, allowSetters = true)
    private LandLord propertyOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "location", "rentals", "houseCharacteristics", "reviews", "propertysPhotos", "tenantPropertyPreferences" },
        allowSetters = true
    )
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RentalAgreement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreements() {
        return this.agreements;
    }

    public RentalAgreement agreements(String agreements) {
        this.setAgreements(agreements);
        return this;
    }

    public void setAgreements(String agreements) {
        this.agreements = agreements;
    }

    public String getDeliveryProtocol() {
        return this.deliveryProtocol;
    }

    public RentalAgreement deliveryProtocol(String deliveryProtocol) {
        this.setDeliveryProtocol(deliveryProtocol);
        return this;
    }

    public void setDeliveryProtocol(String deliveryProtocol) {
        this.deliveryProtocol = deliveryProtocol;
    }

    public Boolean getTenantSign() {
        return this.tenantSign;
    }

    public RentalAgreement tenantSign(Boolean tenantSign) {
        this.setTenantSign(tenantSign);
        return this;
    }

    public void setTenantSign(Boolean tenantSign) {
        this.tenantSign = tenantSign;
    }

    public Boolean getLandLordSigned() {
        return this.landLordSigned;
    }

    public RentalAgreement landLordSigned(Boolean landLordSigned) {
        this.setLandLordSigned(landLordSigned);
        return this;
    }

    public void setLandLordSigned(Boolean landLordSigned) {
        this.landLordSigned = landLordSigned;
    }

    public RentalAgreementStatusEnum getStatus() {
        return this.status;
    }

    public RentalAgreement status(RentalAgreementStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RentalAgreementStatusEnum status) {
        this.status = status;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public RentalAgreement expirationDate(LocalDate expirationDate) {
        this.setExpirationDate(expirationDate);
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public RentalAgreement createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLatest() {
        return this.latest;
    }

    public RentalAgreement latest(LocalDate latest) {
        this.setLatest(latest);
        return this;
    }

    public void setLatest(LocalDate latest) {
        this.latest = latest;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public RentalAgreement deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setRentalAgreement(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setRentalAgreement(this));
        }
        this.payments = payments;
    }

    public RentalAgreement payments(Set<Payment> payments) {
        this.setPayments(payments);
        return this;
    }

    public RentalAgreement addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setRentalAgreement(this);
        return this;
    }

    public RentalAgreement removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setRentalAgreement(null);
        return this;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public RentalAgreement tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public LandLord getPropertyOwner() {
        return this.propertyOwner;
    }

    public void setPropertyOwner(LandLord landLord) {
        this.propertyOwner = landLord;
    }

    public RentalAgreement propertyOwner(LandLord landLord) {
        this.setPropertyOwner(landLord);
        return this;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public RentalAgreement property(Property property) {
        this.setProperty(property);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalAgreement)) {
            return false;
        }
        return getId() != null && getId().equals(((RentalAgreement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalAgreement{" +
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
            "}";
    }
}
