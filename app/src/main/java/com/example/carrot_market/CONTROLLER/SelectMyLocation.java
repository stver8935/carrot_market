package com.example.carrot_market.CONTROLLER;


import androidx.annotation.RequiresApi;

import androidx.fragment.app.FragmentActivity;


import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carrot_market.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectMyLocation extends FragmentActivity implements OnMapReadyCallback {


    //이전의 마커를 지우기 위한 마커 리스트
    protected ArrayList<Marker> markerArrayList;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        //좌표를 주소로 변환하기 위한 geocoder 변수 설정

        markerArrayList = new ArrayList<>();
button=findViewById(R.id.select_my_location_setting_my_location);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SelectMyLocation.this,MainFragment.class);
        startActivity(intent);

    }
});

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {



        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //좌표값을통해 주소값을 한글로 반환 하기 위해 geocoder 객체 설정
        final Geocoder geocoder=new Geocoder(this, Locale.KOREAN);



        //구글 맵에 클릭 이벤트가 발생시 처리구문
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){


            @Override
            public void onMapClick(LatLng click_location) {


                double latitude=click_location.latitude;
                double longitude=click_location.longitude;

                try {
                    if (1<=markerArrayList.size()){
                        markerArrayList.get(0).remove();
                        markerArrayList.remove(0);
                    }


                    //  좌표의 결과 반환 최대 결과물 갯수 1개로 지정
                    List<Address>  geoList= geocoder.getFromLocation(latitude,longitude,1);




                    if (geoList!=null && geoList.size()>0&&geoList.get(0).getAdminArea()!=null) {
                        //클릭한 위치에 마커를 찍는다
                        Marker marker = googleMap.addMarker(new MarkerOptions().
                                position(new LatLng(latitude, longitude)).title(geoList.get(0).getAdminArea()+geoList.get(0).getLocality()).snippet(geoList.get(0).getAddressLine(0)));
                        Log.d("주소", geoList.get(0).toString());
                        marker.showInfoWindow();
                        markerArrayList.add(marker);
                        Toast.makeText(SelectMyLocation.this, "머지?" , Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SelectMyLocation.this, "다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    Toast.makeText(SelectMyLocation.this, "위치를 받을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }

                //클릭한 위치로 카메라를 옮긴다.
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude));
                googleMap.animateCamera(cameraUpdate);
            }
        });



    }


}
