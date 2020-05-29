package com.example.carrot_market.MODEL.DTO;

public class NotifyKeywordItem {
    String text;
    String date;
    String image_path;
    String key_word_key;
    String product_key;

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getKey_word_key() {
        return key_word_key;
    }

    public void setKey_word_key(String key_word_key) {
        this.key_word_key = key_word_key;
    }

    boolean clear=true;

    public boolean getClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }



}
