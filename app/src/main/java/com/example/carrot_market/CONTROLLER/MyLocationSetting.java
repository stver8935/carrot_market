package com.example.carrot_market.CONTROLLER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.carrot_market.R;

public class MyLocationSetting extends AppCompatActivity {
     int select=0;
    Button select_my_location;
    ImageView aaa;
    SeekBar location_around;
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_setting);


        location_around=(SeekBar)findViewById(R.id.select_my_location_seekbar);
        //지역추가 버튼
//        select_my_location=findViewById(R.id.kkk);
        //이미지 변환 버튼
        aaa=findViewById(R.id.my_location_setting_map);
        backbutton=findViewById(R.id.select_my_location_back);
        select_my_location=findViewById(R.id.select_my_location_add);


        //뒤로가기버튼 이벤트 처리
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //지역 선택 버튼 이벤트 처리

        select_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyLocationSetting.this, SearchMyLocation.class);
                startActivity(intent);
            }
        });

//범위 선택 식바의 클릭 이벤트 처리
        location_around.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                    aaa.setImageResource(R.drawable.setting_map);

                }else if(25 <= seekBar.getProgress()&&seekBar.getProgress()<50){
                    seekBar.setProgress(33,true);
                    aaa.setImageResource(R.drawable.setting_map1);
                }else if(50<=seekBar.getProgress()&&seekBar.getProgress()<75){
                    seekBar.setProgress(66,true);
                    aaa.setImageResource(R.drawable.setting_map2);
                }else {
                    seekBar.setProgress(100,true);
                    aaa.setImageResource(R.drawable.setting_map3);
                }

            }
        });












    }
}
