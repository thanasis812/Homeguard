package com.hg.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.hg.domain.Image} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageDTO implements Serializable {

    private Long id;

    private String path;

    private LocalDate createdDate;

    @Lob
    private byte[] updatedDate;

    private String updatedDateContentType;

    private PropertyDTO property;

    private ReviewDTO review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(byte[] updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDateContentType() {
        return updatedDateContentType;
    }

    public void setUpdatedDateContentType(String updatedDateContentType) {
        this.updatedDateContentType = updatedDateContentType;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public void setProperty(PropertyDTO property) {
        this.property = property;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDTO)) {
            return false;
        }

        ImageDTO imageDTO = (ImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, imageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", property=" + getProperty() +
            ", review=" + getReview() +
            "}";
    }
}
