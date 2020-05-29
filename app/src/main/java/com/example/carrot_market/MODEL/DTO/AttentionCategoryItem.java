package com.example.carrot_market.MODEL.DTO;

public class AttentionCategoryItem {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    private String title;
    private Boolean check;
}
