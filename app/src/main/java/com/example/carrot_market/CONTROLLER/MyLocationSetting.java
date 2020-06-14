package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AddressItem;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.SelectMyLocationButtonAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyLocationSetting extends AppCompatActivity {
    private Button select_my_location;
    private ImageView location_range_image;
    private SeekBar location_range;
    private ImageButton backbutton;
    private TextView title,location_info;

    final int CREATE_PRODUCT_RESULT_CODE=2;
    final int LOCATION_SETTING_RESULT_CODE=1;




    private RecyclerView my_location_button_recyclerview;
    private SelectMyLocationButtonAdapter my_location_button_adapter;
    private ArrayList<AddressItem> my_location_button_arraylist=new ArrayList<>();

    private UserInfoSave userinfo;
    LinearLayout select_my_location_linear;
    private int range;
    private boolean add_product_bool=false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_setting);

        userinfo=new UserInfoSave(MyLocationSetting.this);


        //선택한 동네를 설명해주는 타이틀과 범위 설정 식바바
        title =findViewById(R.id.select_my_location_title);
        location_info=findViewById(R.id.select_my_location_info);

        location_range = (SeekBar) findViewById(R.id.select_my_location_seekbar);


        select_my_location_linear = findViewById(R.id.select_my_location_linear);

        //지역추가 버튼
//        select_my_location=findViewById(R.id.kkk);
        //이미지 변환 버튼
        location_range_image = findViewById(R.id.my_location_setting_map);
        backbutton = findViewById(R.id.select_my_location_back);
        my_location_button_recyclerview = findViewById(R.id.select_my_location_button_recycler);
        my_location_button_adapter = new SelectMyLocationButtonAdapter(my_location_button_arraylist,location_info,location_range,location_range_image);
        my_location_button_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        my_location_button_recyclerview.setAdapter(my_location_button_adapter);

        select_my_location_linear.setVisibility(View.VISIBLE);
        title.setText("내 동네 설정하기");





        //뒤로가기버튼 이벤트 처리
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                finish();
            }
        });




    }




    private void GetIntent(){
        final Intent intent=getIntent();
        //상품 판매 액티비티에서 왔을때
        if (intent.getBooleanExtra("product",false)) {
            select_my_location_linear.setVisibility(View.GONE);
            title.setText(intent.getStringExtra("title"));
            add_product_bool=true;
            for (int i=0;i<my_location_button_arraylist.size();i++){
                if (my_location_button_arraylist.get(i).isSelect()){
                    location_info.setText(my_location_button_arraylist.get(i).getLocation()+"반경"+my_location_button_arraylist.get(i).getRange()+"km");
                    switch (my_location_button_arraylist.get(i).getRange()){
                        case 5:
                            location_range.setProgress(0);
                            location_range_image.setImageResource(R.drawable.setting_map);
                            my_location_button_arraylist.get(i).setRange(5);
                            range=20;
                            break;
                        case 10:
                            location_range.setProgress(33);

                            location_range_image.setImageResource(R.drawable.setting_map1);
                            my_location_button_arraylist.get(i).setRange(10);
                            range=10;
                            break;
                        case 15:
                            location_range.setProgress(66);

                            location_range_image.setImageResource(R.drawable.setting_map2);
                            range=15;
                            break;
                        case 20:

                            location_range.setProgress(100);
                            location_range_image.setImageResource(R.drawable.setting_map3);
                            range=20;
                            break;
                    }

                }
            }


            location_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (seekBar.getProgress()<25){
                        seekBar.setProgress(0,true);
                        location_range_image.setImageResource(R.drawable.setting_map);
                        range=5;
                    }else if(25 <= seekBar.getProgress()&&seekBar.getProgress()<50){
                        seekBar.setProgress(33,true);
                        location_range_image.setImageResource(R.drawable.setting_map1);
                        range=10;
                    }else if(50<=seekBar.getProgress()&&seekBar.getProgress()<75){
                        seekBar.setProgress(66,true);

                        location_range_image.setImageResource(R.drawable.setting_map2);
                        range=15;
                    }else {
                        seekBar.setProgress(100,true);
                        location_range_image.setImageResource(R.drawable.setting_map3);
                        range=20;
                    }

                    //위에 타이틀을 없어졌어도 여전히 데이터있는 상태
                    for (int i=0;i<my_location_button_arraylist.size();i++){
                        if (my_location_button_arraylist.get(i).isSelect()){
                            my_location_button_arraylist.get(i).setRange(range);
                            location_info.setText(my_location_button_arraylist.get(i).getLocation()+"반경"+my_location_button_arraylist.get(i).getRange()+"km");
                        }
                    }



                }
            });

            //상품 등록인가 아니면 반경 설정인가
        }else {
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        try {
            my_location_setting();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        GetIntent();
    }
    public void my_location_setting() throws JSONException, ExecutionException, InterruptedException {

        JSONArray jsonArray=new JSONArray();
        MyLocationLoadTask task=new MyLocationLoadTask();


        jsonArray=new JSONArray(task.execute(userinfo.return_account().getId()).get());


        if (jsonArray.length()==0) {

        }else {

        }

       if (my_location_button_arraylist.size()!=0) {
           my_location_button_adapter.notifyItemRangeRemoved(0, my_location_button_arraylist.size());
           my_location_button_arraylist.clear();
           Log.e("del", "del"+my_location_button_arraylist.size());
       }


       if (my_location_button_arraylist.size()!=jsonArray.length()){
       for (int i=my_location_button_arraylist.size();i<jsonArray.length();i++) {
           AddressItem item = new AddressItem();
           try {

               item.jsonobject_to_AddressItem(jsonArray.getJSONObject(i));
               my_location_button_arraylist.add(item);
            my_location_button_adapter.notifyItemInserted(i);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
       }

       if (0<=my_location_button_arraylist.size()&&my_location_button_arraylist.size()<=1){
           AddressItem item=new AddressItem();
           item.setLocation("");

           my_location_button_arraylist.add(item);
        my_location_button_adapter.notifyItemInserted(my_location_button_arraylist.size());
       }

       my_location_button_adapter.notifyDataSetChanged();


    }

    @Override
    public void finish() {
        if (add_product_bool){

            //디스트로이에서는 전달이 안된다 포즈 상태때 확인 하기

            for (int i=0;i<my_location_button_arraylist.size();i++){
            if (my_location_button_arraylist.get(i).isSelect()) {
                Intent intent2 = new Intent(MyLocationSetting.this, AddProduct.class);
                intent2.putExtra("add_prodct_location_range", my_location_button_arraylist.get(i).getRange());
                intent2.putExtra("add_product_location",my_location_button_arraylist.get(i).getLocation());
                setResult(CREATE_PRODUCT_RESULT_CODE, intent2);
                Log.e("상품 종료실행", "실행");
            }
            }
        }else {
            setResult(LOCATION_SETTING_RESULT_CODE);
        }

        super.finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
