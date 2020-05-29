package com.example.carrot_market.MODEL.DTO;

public class ProductComentItem {
    String key;
    String id;
    String location;
    String date;
    String profile_image_path;

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    String coment;



    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;

}
