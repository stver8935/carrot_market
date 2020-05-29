package com.example.carrot_market.CONTROLLER;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AddressItem;
import com.example.carrot_market.MODEL.HttpConnect.TESTTASK;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.SearchMYLocationAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMyLocation extends AppCompatActivity {

    ImageButton backbutton;
    Button mylocation;
    EditText insert_address;
    Geocoder geocoder;
    SearchMYLocationAdapter search_my_location_adapter;
    RecyclerView search_recycler_view;
    LinearLayoutManager linearLayoutManager;
    ArrayList<AddressItem> arrayList=new ArrayList<>();


    ScrollView scrollView;

    int page_count=1;






    private String key = "5416f0d72e3fb7ff01576625140319";
    private String putAddress;
    //우체국으로부터 반환 받은 우편주소 리스트
    private ArrayList<String> addressSearchResultArr = new ArrayList<>();
    JSONArray array=new JSONArray();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__my__location);


        final Intent intent=getIntent();


        //리사이클러뷰 세팅 부분
        search_recycler_view=findViewById(R.id.search_my_location_recyclerview);

        search_my_location_adapter=new SearchMYLocationAdapter(arrayList, intent.getBooleanExtra("button_update",false),intent.getStringExtra("before_address"));


        linearLayoutManager=new LinearLayoutManager(this);

        search_recycler_view.setLayoutManager(linearLayoutManager);
        search_recycler_view.setAdapter(search_my_location_adapter);
        scrollView=findViewById(R.id.search_my_location_scrollview);

        //검색을 위한 지오 코더 변수 초기화
        // 생성자 부분에 현 액티비티의 컨택스트를 넣어주고
        //주소를 한국어 설정으로 지정해준다.
        geocoder=new Geocoder(this,Locale.KOREAN);






        //버튼 초기화 부분
        // 뒤로가기버튼 초기화
        backbutton = findViewById(R.id.search_my_location_back);
        //현재위치 기준 검색 버튼 초기화
        mylocation = findViewById(R.id.search_my_location_my_location);
        //검색을 위한 주소 삽입 에딧 텍스트 초기화
        insert_address = findViewById(R.id.search_my_location_insert_address);





        //뒤로가기버튼 클릭 이벤트 처리
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder=new Geocoder(SearchMyLocation.this);


                List<Address> list=null;
                try {


                    String text=insert_address.getText().toString();

                    String test=text;
                list=geocoder.getFromLocationName(test,1000);

                Log.e("address",""+list);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });




        //현재 위치 기준으로 리스트 나열 하는 버튼 이벤트 처리
        mylocation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getApplication().getSystemService(SearchMyLocation.this.LOCATION_SERVICE);
                String Location_provider = locationManager.GPS_PROVIDER;

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                Location location = locationManager.getLastKnownLocation(Location_provider);

                insert_address.setText(""+location);
                Toast.makeText(SearchMyLocation.this, ""+location, Toast.LENGTH_SHORT).show();

            }
        });


        search_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int last=((LinearLayoutManager)search_recycler_view.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemcount=search_recycler_view.getAdapter().getItemCount();

                if (last>itemcount-1){

                    page_count++;
                    TESTTASK testtask = new TESTTASK(handler,SearchMyLocation.this,"" + insert_address.getText(),page_count);
                    Thread thread = new Thread(testtask);
                    thread.setDaemon(true);
                    thread.start();
                }

            }
        });
        //입력 주소에따른 검색 이벤트 처리를 위한 whatcher

        insert_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //텍스트가 바뀌기전에 이벤트처리
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //텍스트가 바뀐 시검에 이벤트 처리
            }

            @Override
            public void afterTextChanged(Editable s) {
                //텍스트가 바뀌고 난후의 이벤트 처리


                arrayList.clear();
                search_my_location_adapter.notifyDataSetChanged();
                page_count=1;
                TESTTASK testtask = new TESTTASK(handler,SearchMyLocation.this, "" + insert_address.getText(),page_count);
                Thread thread = new Thread(testtask);
                thread.setDaemon(true);
                thread.start();
            }
        });


    }


    Handler handler=new Handler() {
        @Override

        public void handleMessage(Message message) {

            switch (message.what) {
                case 0:


                    JSONArray jsonArray = null;
                        try {
                        jsonArray=new JSONArray(message.getData().getString("address"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int a=0;a<jsonArray.length();a++) {
                        AddressItem item= null;

                            item = new AddressItem();

                        try {

                        item.setAddress(jsonArray.getJSONObject(a).getString("address"));
                        arrayList.add(item);
                        search_my_location_adapter.notifyItemInserted(a);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("json",""+array);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                    break;

                case 1:
                    Log.e("messssssssss", "" + message.getData().getString("hello"));
                    break;
            }

        }


    };

}
