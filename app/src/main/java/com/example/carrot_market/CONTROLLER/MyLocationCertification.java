package com.example.carrot_market.CONTROLLER;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.carrot_market.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;

public class MyLocationCertification extends FragmentActivity implements OnMapReadyCallback {

    ImageButton back;
    TextView title, warning_text, location_list, location_explanation, change_location;

    //현재 위치를 받아오는 버튼
    Button my_location;
    private GoogleMap mMap;
    LocationManager mLM ;


    MarkerOptions markerOptions = new MarkerOptions();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_certification);


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
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.my_location_certification);
        mapFragment.getMapAsync(this);

        int permiCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permiCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MyLocationCertification.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        }

//권한 있다면
        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




        //뒤로가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        final Geocoder geocoder = new Geocoder(this, Locale.KOREAN);

        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MyLocationCertification.this, "update", Toast.LENGTH_SHORT).show();
//                mMap.
                FusedLocationProviderClient fusedLocationClient;
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(MyLocationCertification.this);
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MyLocationCertification.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    LatLng Mylocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    // 카메라를 여의도 위치로 옮겨준다
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Mylocation, 16));
                                    Geocoder geocoder = new Geocoder(MyLocationCertification.this, Locale.KOREAN);
                                    try {
                                        mMap.clear();
                                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        markerOptions.position(Mylocation);
                                        mMap.addMarker(markerOptions);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });


            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng seoul = new LatLng(37.52487, 126.92723);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));
        markerOptions.position(seoul);
        mMap.addMarker(markerOptions);



    }


    public void oneMarker() {
        // 서울 여의도에 대한 위치 설정
        LatLng seoul = new LatLng(37.52487, 126.92723);
        // 카메라를 여의도 위치로 옮겨준다
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

        // 구글맵에 표시할 마커에 대한 옵션 설정
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다")
                .snippet("여기는 여의도입니다")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f); // 알파는 좌표의 투명도

        // 마커를 생성한다
        mMap.addMarker(markerOptions); // .showInfoWindow() 를 쓰면 처음부터 마커에 상세정보가 뜬다

        // 정보창 클릭리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        // 마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        // 카메라를 여의도 위치로 옮겨준다
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MyLocationCertification.this, "눌렀습니다!!", Toast.LENGTH_LONG);
                return false;
            }
        });
    }

    // 정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String markerId = marker.getId();
            Toast.makeText(MyLocationCertification.this, "정보창 클릭 Marker ID : "
                    + markerId, Toast.LENGTH_SHORT).show();
        }
    };

    // 마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            // 선택한 타겟의 위치
            LatLng location = marker.getPosition();
            Toast.makeText(MyLocationCertification.this, "마커 클릭 Marker ID : "
                            + markerId + "(" + location.latitude + " " + location.longitude + ")",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    };


//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//
//
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMinZoomPreference(14.0f);
//        googleMap.setMaxZoomPreference(14.0f);
//
//
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//
//            return;
//        }
//
//
//        LocationManager locationManager = (LocationManager) getApplication().getSystemService(getApplication().LOCATION_SERVICE);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//
//        final Geocoder geocoder = new Geocoder(this, Locale.KOREAN);

    //
//
//        double lng = 0;
//        double lat = 0;
//
//
//            lng = location.getLongitude();
//            lat = location.getLatitude();
//
//            Toast.makeText(this, "현재 위치를 불러 올수 없습니다.", Toast.LENGTH_SHORT).show();
//
//
//        try {
//            List<Address> geoList = geocoder.getFromLocation(lat, lng, 1);
//
//            title.setText(geoList.get(0).getThoroughfare() + " 동네인증");
////            if (geoList.get(0).getThoroughfare().equals("인현동")) {
////                warning_text.setVisibility(GONE);
////            }else {
////                warning_text.setVisibility(View.VISIBLE);
////            }
//
//
//            Toast.makeText(this, "" + geoList.get(0).getThoroughfare(), Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        LatLng latLng = new LatLng(lat, lng);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
//        googleMap.moveCamera(cameraUpdate);
//
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(lat, lng)));
//
//
//        //my_locaion 버튼을 클릭시 지도상에 현재위치에 마커를 찍기 위한 버튼 이벤트 처리 구문
//        my_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LocationManager locationManager = (LocationManager) getApplication().getSystemService(getApplication().LOCATION_SERVICE);
//                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    Activity#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for Activity#requestPermissions for more details.
//                    return;
//                }
//                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//
//                LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
//                googleMap.moveCamera(cameraUpdate);
//
//
//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(location.getLatitude(),location.getLongitude())));
//
//
//
//
//
//            }
//        });
//
//
//
//    }


    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 0) {

            if (grantResults[0] == 0) {

                Toast.makeText(this, "위치 정보 허용 승인", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "위치 정보 허용 거부", Toast.LENGTH_SHORT).show();

            }

        }

    }

}
