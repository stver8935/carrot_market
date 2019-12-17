package com.example.carrot_market.MODEL.DTO;

import android.graphics.Bitmap;

public class ProductSellerProductListItem {
    Bitmap product_image;
    String product_title;
    String product_price;

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    String product_key;
    public Bitmap getProduct_image() {
        return product_image;
    }

    public void setProduct_image(Bitmap product_image) {
        this.product_image = product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }


}
