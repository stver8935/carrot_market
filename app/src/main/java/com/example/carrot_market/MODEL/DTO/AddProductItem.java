package com.example.carrot_market.MODEL.DTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddProductItem {

    //AddProductImageItem 은 uri를 갖고 있느 아이템 클레스 입니다.

    private ArrayList<AddProductImageItem> imageItemArrayList;
    private String title,category,text;
    private int price;

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    private int range;
    private boolean deal;

    public ArrayList<AddProductImageItem> getImageItemArrayList() {
        return imageItemArrayList;
    }

    public void setImageItemArrayList(ArrayList<AddProductImageItem> imageItemArrayList) {
        this.imageItemArrayList = imageItemArrayList;
    }
    public JSONArray getImageitemArrayList_convert_JSONArray(){
        JSONArray jsonArray=new JSONArray();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("title",title);
        jsonObject.put("range",range);
        jsonObject.put("text",text);
        jsonObject.put("price",price);
        jsonObject.put("deal",deal);
        jsonObject.put("category",category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        return jsonArray;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }


    public JSONObject return_jsonobj(){

//        title range category text price deal id
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("title",title);
        jsonObject.put("text",text);
        jsonObject.put("category",category);
        jsonObject.put("price",price);
        jsonObject.put("deal",deal);
        jsonObject.put("range",range);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
