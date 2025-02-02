package com.hg.service.dto.mydto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A User DTO for the {@link com.hg.domain.Payment} entity.
 */
public class UserPaymentDTO implements Serializable {

    private Long id;
    private Float price;
    private String name;
    private Long houseId;
    private LocalDate paymentDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
