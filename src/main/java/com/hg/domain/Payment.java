package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.PaymentStatusEnum;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Float price;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "extended_date")
    private LocalDate extendedDate;

    @Column(name = "payed_date")
    private Instant payedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatusEnum status;

    @Column(name = "retry")
    private Integer retry;

    @Column(name = "payment_id")
    private String paymentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "payments", "tenant", "propertyOwner", "property" }, allowSetters = true)
    private RentalAgreement rentalAgreement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return this.price;
    }

    public Payment price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Payment createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExtendedDate() {
        return this.extendedDate;
    }

    public Payment extendedDate(LocalDate extendedDate) {
        this.setExtendedDate(extendedDate);
        return this;
    }

    public void setExtendedDate(LocalDate extendedDate) {
        this.extendedDate = extendedDate;
    }

    public Instant getPayedDate() {
        return this.payedDate;
    }

    public Payment payedDate(Instant payedDate) {
        this.setPayedDate(payedDate);
        return this;
    }

    public void setPayedDate(Instant payedDate) {
        this.payedDate = payedDate;
    }

    public PaymentStatusEnum getStatus() {
        return this.status;
    }

    public Payment status(PaymentStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }

    public Integer getRetry() {
        return this.retry;
    }

    public Payment retry(Integer retry) {
        this.setRetry(retry);
        return this;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public String getPaymentID() {
        return this.paymentID;
    }

    public Payment paymentID(String paymentID) {
        this.setPaymentID(paymentID);
        return this;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public RentalAgreement getRentalAgreement() {
        return this.rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public Payment rentalAgreement(RentalAgreement rentalAgreement) {
        this.setRentalAgreement(rentalAgreement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", extendedDate='" + getExtendedDate() + "'" +
            ", payedDate='" + getPayedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", retry=" + getRetry() +
            ", paymentID='" + getPaymentID() + "'" +
            "}";
    }
}
