package com.example.carrot_market.MODEL.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealReviewLeaveItem {
    @SerializedName("check")
    @Expose
    Boolean check=false;
    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("value")
    @Expose
    int value;


    public DealReviewLeaveItem(String title) {
        this.title = title;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
