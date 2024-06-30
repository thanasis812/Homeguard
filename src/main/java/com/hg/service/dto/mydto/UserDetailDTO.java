package com.hg.service.dto.mydto;

public class UserDetailDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private byte[] image;
    private String category;
    boolean phoneAvailable;
    private PropertyDossierDTO[] houses;
    private UserReviewDTO[] reviews;
    boolean haveEconomicRelationship;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPhoneAvailable() {
        return phoneAvailable;
    }

    public void setPhoneAvailable(boolean phoneAvailable) {
        this.phoneAvailable = phoneAvailable;
    }

    public PropertyDossierDTO[] getHouses() {
        return houses;
    }

    public void setHouses(PropertyDossierDTO[] houses) {
        this.houses = houses;
    }

    public UserReviewDTO[] getReviews() {
        return reviews;
    }

    public void setReviews(UserReviewDTO[] reviews) {
        this.reviews = reviews;
    }

    public boolean isHaveEconomicRelationship() {
        return haveEconomicRelationship;
    }

    public void setHaveEconomicRelationship(boolean haveEconomicRelationship) {
        this.haveEconomicRelationship = haveEconomicRelationship;
    }
}
