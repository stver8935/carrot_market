package com.example.carrot_market.CONTROLLER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;

public class MyLocationCertification extends FragmentActivity implements OnMapReadyCallback {

    ImageButton back;
    TextView title, warning_text, location_list, location_explanation, change_location;

    //현재 위치를 받아오는 버튼
    Button my_location;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_certification);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.my_location_certification);

        mapFragment.getMapAsync(this);


        back = findViewById(R.id.my_location_certification_back);
        title = findViewById(R.id.my_location_certification_title);

        warning_text = findViewById(R.id.my_location_certification_warning_text);

        location_list = findViewById(R.id.my_location_certification_location_list);
        location_explanation = findViewById(R.id.my_location_certification_location_explanation);

        //이전에 내가 지정한 위치를 현재 위치로 바꾸는 버튼
        change_location = findViewById(R.id.my_location_certification_change_location);

        //지도상에 현재위치를 표시해주는 버튼
        my_location = findViewById(R.id.my_location_certification_my_location);

        warning_text.setVisibility(View.INVISIBLE);

        //뒤로가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Geocoder geocoder = new Geocoder(this, Locale.KOREAN);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMinZoomPreference(14.0f);
        googleMap.setMaxZoomPreference(14.0f);


        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions

            return;
        }


        LocationManager locationManager = (LocationManager) getApplication().getSystemService(getApplication().LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        final Geocoder geocoder = new Geocoder(this, Locale.KOREAN);


        double lng = 0;
        double lat = 0;


            lng = location.getLongitude();
            lat = location.getLatitude();

            Toast.makeText(this, "현재 위치를 불러 올수 없습니다.", Toast.LENGTH_SHORT).show();


        try {
            List<Address> geoList = geocoder.getFromLocation(lat, lng, 1);

            title.setText(geoList.get(0).getThoroughfare() + " 동네인증");
//            if (geoList.get(0).getThoroughfare().equals("인현동")) {
//                warning_text.setVisibility(GONE);
//            }else {
//                warning_text.setVisibility(View.VISIBLE);
//            }


            Toast.makeText(this, "" + geoList.get(0).getThoroughfare(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        googleMap.moveCamera(cameraUpdate);


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng)));


        //my_locaion 버튼을 클릭시 지도상에 현재위치에 마커를 찍기 위한 버튼 이벤트 처리 구문
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getApplication().getSystemService(getApplication().LOCATION_SERVICE);
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
                googleMap.moveCamera(cameraUpdate);


                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(),location.getLongitude())));





            }
        });



    }
}
