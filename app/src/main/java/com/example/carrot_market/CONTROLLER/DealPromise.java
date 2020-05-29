package com.example.carrot_market.CONTROLLER;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carrot_market.CONTROLLER.METHOD.AlramTimeCallback;
import com.example.carrot_market.MODEL.DTO.AlramTimeItem;
import com.example.carrot_market.MODEL.DTO.DateTimeItem;
import com.example.carrot_market.MODEL.HttpConnect.SendMessageTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DealPromise extends AppCompatActivity  {


    private ImageButton back;
    private Button commit;
    public TextView deal_promise_dialog,alram;
    private Switch alram_switch;
    String date_to_string;
    Date date;
    Calendar cal=Calendar.getInstance();
    DateTimeItem calender_item=new DateTimeItem(cal);

    AlramTimeItem select_alram_time_item=new AlramTimeItem();


    Intent intent;
    UserInfoSave userInfoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_promise);


        back=findViewById(R.id.deal_promise_back_button);
        commit=findViewById(R.id.deal_promise_commit);
        deal_promise_dialog=findViewById(R.id.deal_promise_call_dialog);
        alram=findViewById(R.id.deal_promise_alram_time);
        alram_switch=findViewById(R.id.deal_promise_alram_switch);


        //알람 시간 기본값 초기화
        select_alram_time_item.setMinite(30);
        select_alram_time_item.setTime_text(30+" 분 전");

        userInfoSave=new UserInfoSave(DealPromise.this);

        deal_promise_dialog.setText(calender_item.return_to_string());


        //채팅창에서 받아온 데이터
        //product_key;
        //opponent


        intent=getIntent();


        final AlramTimeCallback alramTimeCallback=new AlramTimeCallback();

        AlramTimeCallback.callback callback=new AlramTimeCallback.callback() {
            @Override
            public void onCallbackListener(AlramTimeItem item) {
                //id

                intent=getIntent();

            alram.setText(item.getTime_text());


            }
        };

        alramTimeCallback.setCallback(callback);


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    alram_commit(calender_item.return_calendar(),select_alram_time_item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

alram_switch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



    }
});



        // 알람 시간 몇분전인지 설정

        alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DealPromise.this,AlramTimeDialog.class);
                startActivityForResult(intent,1);
//
//                alram_time_dial= new AlramTimeDialog(DealPromise.this,alram); // 왼쪽 버튼 이벤트
//                alram_time_dial.setCancelable(true);
//                alram_time_dial.getWindow().setGravity(Gravity.TOP);
//                alram_time_dial.show();

            }
        });

        //뒤로 가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();



            }
        });


        //알람 일자 설정
        deal_promise_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_date_picker();

            }
        });


    }





    //날짜 설정 다이얼로그
    public void show_date_picker(){

        DatePickerDialog dialog = new DatePickerDialog(DealPromise.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year_dial, int month_dial, int date_dial) {

                calender_item.setYear(year_dial);
                calender_item.setMonth(month_dial);
                calender_item.setDay(date_dial);


                Log.e("date_picker","year"+year_dial+"--month"+month_dial+"--date"+date_dial);
                show_time_picker();


            }
        }, calender_item.getYear(), calender_item.getMonth(), calender_item.getDay());

//                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
        dialog.getDatePicker().setMinDate(new Date().getTime());

        dialog.show();



    }

    //시간 설정 다이얼로그
    public void show_time_picker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour_dial, int minute_dial) {



                calender_item.setHour(hour_dial);
                calender_item.setMin(minute_dial);
//
                deal_promise_dialog.setText(calender_item.return_to_string());


            }
        };


        TimePickerDialog timePickerDialog =
                new TimePickerDialog(DealPromise.this,onTimeSetListener, calender_item.getHour(),calender_item.getMin(), true);
        timePickerDialog.setTitle("약속시간 .");
        timePickerDialog.show();
        

    }




//
//
//    //알람 설정시 사용하는 메서드
//    //설정한 시간으로 부터 시간전에 알람이 올것인지 체크
//    //알람을 받을것인지 안받을것인지 설정한 switch 체크
//    //현재 시간보다 알람 시간이 작은지 체크

public void alram_commit(Calendar cal,AlramTimeItem select_alram_time_item) throws JSONException {
    //매개 변수로 받은 cal은 내가 설정한 calendar의 시간이다
    //몇분전에 알람받을것인지도 매개 변수로 받아 현재 시간과 비교한다

    //calendar_item이 내가 설정한 시간이고
    //cal_temp 가 알람 받길 원하는 시간대 이다.
    //calendar_item 이 현재 시간이후로 선택하게 되어 있으므로
    //현재 시간과 cal_temp를 비교하여 현재 시간보다 작으면 설정이 안되게 해야 한다.
    //현재시간 보다 크거나 같으면 설정이 되게 한다;.

    //알람 받을 시간을 현재 시간과 비교하기위한 datetime 형 날짜 데이터
    Date date=cal.getTime();
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
    String date_format=format.format(date);

    //현재 시간
    Date now_time=Calendar.getInstance().getTime();


    //1 - 현재시간이 더 클때
    //-- 이하 상황은 데이터 처리가 같음
    //0 -현재 시간과 같을떄
    // -1 설정한 알람 시간이 더 클때


    switch (now_time.compareTo(date))
    {
        //설정한 시간이 현재 시간 보다 작을때
        case 1:
            Toast.makeText(DealPromise.this, "현재 시간 이전으로 설정 할 수 없습니다.", Toast.LENGTH_SHORT).show();
            Log.e("알람 설정 실패!","현재 시간"+now_time+"설정한 시간"+date+"선택한 몇분전"+select_alram_time_item.getMinite());
            break;
        case 0:
            Toast.makeText(DealPromise.this, "현재 시간 이전으로 설정 할 수 없습니다.", Toast.LENGTH_SHORT).show();

            break;
        default:
            JSONObject alram_json_object=new JSONObject();
            //알람을 위한 메시지
            alram_json_object.put("date_format",date_format);
            alram_json_object.put("alram_time_min",select_alram_time_item.getMinite());

            JSONObject jsonObject=new JSONObject(getIntent().getStringExtra("deal_promise_message"));
            jsonObject.put("message_type","0");
            jsonObject.put("message",alram_json_object);
            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(now_time);
//

            select_alram_time_item.getMinite();

            Calendar calendar1=Calendar.getInstance();
            calendar1.setTime(date);
            calendar1.add(Calendar.MINUTE,-select_alram_time_item.getMinite());
            date=calendar1.getTime();
            calendar.setTime(date);

            if (alram_switch.isChecked()){
//            AlramNotification alramNotification=new AlramNotification(this,jsonObject.toString());
//            alramNotification.send_notification();

        }else {

        }

            Toast.makeText(DealPromise.this, "약속 시간이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                Log.e("time_setting","전송할 데이터"+jsonObject.toString());

                    //전송할 데이터{"product_key":"159","id":"stver8935","opponent":"gksrlgud","message_type":"0","message":"2020-03-18 22:10:15"}
                    SendMessageTask sendMessageTask=new SendMessageTask(jsonObject.toString());
                    Thread thread=new Thread(sendMessageTask);
                    thread.run();
                    finish();
            break;

    }

    Log.e("error",""+ now_time.compareTo(date));
    Log.e("error2",""+now_time.compareTo(now_time));
    ;
    Log.e("error3",""+date.compareTo(now_time));
    Log.e("date",""+date);
    Log.e("now_time",""+now_time);
    Log.e("check",""+alram_switch.isChecked());
//
//    calender_item.return_calendar().compareTo(cal_temp);

}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==1){

            if (resultCode==1){

                try {
                    JSONObject alram_time_json=new JSONObject(data.getStringExtra("alram_time_data"));
                    Log.e("alram_min",alram_time_json.toString());

                    select_alram_time_item.setTime_text(alram_time_json.getString("time_text"));
                    select_alram_time_item.setMinite(alram_time_json.getInt("minite"));

                    alram.setText(select_alram_time_item.getTime_text());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}
