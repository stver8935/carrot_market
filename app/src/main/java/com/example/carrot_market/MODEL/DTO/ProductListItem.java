package com.example.carrot_market.MODEL.DTO;

public class ProductListItem {
    String title;
    String location;
    String time;
    String price;
    String product_key;
    String id;
    int image;
    int chat_count;
    int favorite_count;
    int coment_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getComent_count() {
        return coment_count;
    }

    public void setComent_count(int coment_count) {
        this.coment_count = coment_count;
    }

    public String getPoduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



    public int getChat_count() {
        return chat_count;
    }

    public void setChat_count(int chat_count) {
        this.chat_count = chat_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ProductListItem(String title, String location, String time, String price) {
        this.title = title;
        this.location = location;
        this.time = time;
        this.price = price;
    }


}
