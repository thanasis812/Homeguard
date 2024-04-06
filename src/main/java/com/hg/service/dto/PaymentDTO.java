package com.hg.service.dto;

import com.hg.domain.enumeration.PaymentStatusEnum;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    private Float price;

    private LocalDate createdDate;

    private LocalDate extendedDate;

    private Instant payedDate;

    private PaymentStatusEnum status;

    private Integer retry;

    private String paymentID;

    private RentalAgreementDTO rentalAgreement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExtendedDate() {
        return extendedDate;
    }

    public void setExtendedDate(LocalDate extendedDate) {
        this.extendedDate = extendedDate;
    }

    public Instant getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Instant payedDate) {
        this.payedDate = payedDate;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public RentalAgreementDTO getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreementDTO rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", extendedDate='" + getExtendedDate() + "'" +
            ", payedDate='" + getPayedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", retry=" + getRetry() +
            ", paymentID='" + getPaymentID() + "'" +
            ", rentalAgreement=" + getRentalAgreement() +
            "}";
    }
}
