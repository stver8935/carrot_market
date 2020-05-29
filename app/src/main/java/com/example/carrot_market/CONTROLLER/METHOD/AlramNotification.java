package com.example.carrot_market.CONTROLLER.METHOD;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.carrot_market.CONTROLLER.Receiver.AlramReceiver;

import org.json.JSONException;
import org.json.JSONObject;

public class AlramNotification {

    JSONObject alram_message_json;
    Context context;

    public AlramNotification(Context context,String alram_message) throws JSONException {

        //alram_message 데이터 구조
        //id 보낸 사람 아이디
        //chatting_room_Key 채팅룸 키
        //opponent 받는 사람 아이디
        //alram_min 약속시간으로부터 몇분 전에 알람을 줄건지 데이터
        //chat_room_key":"49","product_key":"159","id":"stver8935","opponent":"gksrlgud","message_type":"0","message":"2020-03-18 22:14:45
        //calendar 약속이 적힌 달력
        this.context=context;
         alram_message_json=new JSONObject(alram_message);
    }
    public void send_notification() throws JSONException {


        Log.e("alram_messag_json",""+alram_message_json);


        Boolean dailyNotify = true; // 무조건 알람을 사용

        Intent alramIntent = new Intent(context, AlramReceiver.class);

        alramIntent.putExtra("alram_message_json",alram_message_json.toString());
        alramIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alramIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        JSONObject time_json=new JSONObject(alram_message_json.getString("message"));
        StringToDate stringtodate_converter=new StringToDate(time_json.getString("date_format"),time_json.getString("alram_time_min"));


        alarmManager.set(AlarmManager.RTC_WAKEUP,stringtodate_converter.natural_convert().getTime(),
                pendingIntent);

        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {
                //실질적 알람 설정
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,stringtodate_converter.natural_convert().getTime(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,stringtodate_converter.natural_convert().getTime(), pendingIntent);
                }
            }

        }

    }

}
