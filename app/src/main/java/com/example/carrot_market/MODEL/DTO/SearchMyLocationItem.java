package com.example.carrot_market.MODEL.DTO;

import com.google.android.gms.maps.model.LatLng;

public class SearchMyLocationItem {
    private String address;
    private LatLng latLng;
    private String feat;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getFeat() {
        return feat;
    }

    public void setFeat(String feat) {
        this.feat = feat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
