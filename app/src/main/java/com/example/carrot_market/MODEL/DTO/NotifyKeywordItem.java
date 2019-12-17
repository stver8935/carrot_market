package com.example.carrot_market.MODEL.DTO;

public class NotifyKeywordItem {
    String text;
    String date;
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


    public NotifyKeywordItem(String text, String date) {
        this.text = text;
        this.date = date;
    }


}
