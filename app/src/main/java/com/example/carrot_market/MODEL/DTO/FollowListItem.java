package com.example.carrot_market.MODEL.DTO;

public class FollowListItem {
    String id;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location;


    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String profile_image_path;
    String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollow_check() {
        return follow_check;
    }

    public void setFollow_check(String follow_check) {
        this.follow_check = follow_check;
    }

    String follow_check;
}
