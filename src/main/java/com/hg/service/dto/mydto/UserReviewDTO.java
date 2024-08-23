package com.hg.service.dto.mydto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * A User DTO for the {@link com.hg.domain.Review} entity.
 */
public class UserReviewDTO implements Serializable {

    private Long id;
    private Long userId;
    private String name;
    private byte[] userImage;
    private Set<byte[]> images;
    private Date reviewDate;
    private Integer stars;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public Set<byte[]> getImages() {
        return images;
    }

    public void setImages(Set<byte[]> images) {
        this.images = images;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
