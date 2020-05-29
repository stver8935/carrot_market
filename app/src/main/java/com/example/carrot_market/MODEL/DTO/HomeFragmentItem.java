package com.example.carrot_market.MODEL.DTO;

import android.graphics.Bitmap;

public class HomeFragmentItem {
    int product_key;
    int chatting_count;
    int favorite_count;
    String id;
    int comnet_count;
    String title;
    String location;
    String date;
    int price;

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    String hidden;

    String sales_completed;


    public String getSales_completed() {
        return sales_completed;
    }

    public void setSales_completed(String sales_completed) {
        this.sales_completed = sales_completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getComnet_count() {
        return comnet_count;
    }

    public void setComnet_count(int comnet_count) {
        this.comnet_count = comnet_count;
    }


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    String image_path;
    Bitmap image;



    public int getProduct_key() {
        return product_key;
    }

    public void setProduct_key(int product_key) {
        this.product_key = product_key;
    }

    public int getChatting_count() {
        return chatting_count;
    }

    public void setChatting_count(int chatting_count) {
        this.chatting_count = chatting_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }




    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
