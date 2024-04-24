package com.hg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hg.domain.enumeration.HouseCharacteristicsEnum;
import com.hg.domain.enumeration.HouseCharacteristicsGroupEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A HouseCharacteristics.
 */
@Entity
@Table(name = "house_characteristics")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HouseCharacteristics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private HouseCharacteristicsEnum code;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_group")
    private HouseCharacteristicsGroupEnum group;

    @Column(name = "primary_to_user")
    private Boolean primaryToUser;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "location", "rentals", "houseCharacteristics", "reviews", "propertysPhotos", "landLord", "tenantPropertyPreferences" },
        allowSetters = true
    )
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HouseCharacteristics id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HouseCharacteristicsEnum getCode() {
        return this.code;
    }

    public HouseCharacteristics code(HouseCharacteristicsEnum code) {
        this.setCode(code);
        return this;
    }

    public void setCode(HouseCharacteristicsEnum code) {
        this.code = code;
    }

    public HouseCharacteristicsGroupEnum getGroup() {
        return this.group;
    }

    public HouseCharacteristics group(HouseCharacteristicsGroupEnum group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(HouseCharacteristicsGroupEnum group) {
        this.group = group;
    }

    public Boolean getPrimaryToUser() {
        return this.primaryToUser;
    }

    public HouseCharacteristics primaryToUser(Boolean primaryToUser) {
        this.setPrimaryToUser(primaryToUser);
        return this;
    }

    public void setPrimaryToUser(Boolean primaryToUser) {
        this.primaryToUser = primaryToUser;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public HouseCharacteristics deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public HouseCharacteristics property(Property property) {
        this.setProperty(property);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseCharacteristics)) {
            return false;
        }
        return getId() != null && getId().equals(((HouseCharacteristics) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseCharacteristics{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", group='" + getGroup() + "'" +
            ", primaryToUser='" + getPrimaryToUser() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
