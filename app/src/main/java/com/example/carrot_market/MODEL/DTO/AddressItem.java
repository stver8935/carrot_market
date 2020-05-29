package com.example.carrot_market.MODEL.DTO;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressItem {
    private Double lat,lng;
    private String address="",location;
    private int range;
    private boolean select;

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public JSONObject return_jsonobject(){
    JSONObject jsonObject=new JSONObject();
        try {
        jsonObject.put("address",address);
        jsonObject.put("location",location);
        jsonObject.put("lat",lat);
        jsonObject.put("lng",lng);

        if (range<=0) {
            range=5;
            jsonObject.put("range", range);
        }
        jsonObject.put("select",select);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    return jsonObject;
    }

    public void jsonobject_to_AddressItem(JSONObject jsonObject){
        try {
            address=jsonObject.getString("address");
            location=jsonObject.getString("location");
            lat=jsonObject.getDouble("lat");
            lng=jsonObject.getDouble("lng");


            //초기에 범위설정이 되지 않아서 값이 없다고 나옴 0값 리턴  조건 처리 해주시길..

             range = jsonObject.getInt("range");


            Log.e("select",""+jsonObject.getString("select"));

            if (jsonObject.getString("select").equals("1")){
                select=true;
            }
            else if (jsonObject.getString("select").equals("0")){
                select=false;
            }else {
                select=jsonObject.getBoolean("select");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
