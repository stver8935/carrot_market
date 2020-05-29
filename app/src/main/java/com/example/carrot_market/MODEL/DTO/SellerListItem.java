package com.example.carrot_market.MODEL.DTO;

public class SellerListItem {
    String id;
    String profile_image;
    String address;
    String location;

    public boolean isReview_commit_check() {
        return review_commit_check;
    }

    public void setReview_commit_check(boolean review_commit_check) {
        this.review_commit_check = review_commit_check;
    }

    boolean review_commit_check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
