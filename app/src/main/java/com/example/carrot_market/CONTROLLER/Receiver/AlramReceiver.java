package com.example.carrot_market.CONTROLLER.Receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.carrot_market.CONTROLLER.SplashActivity;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getActivity;

public class AlramReceiver extends BroadcastReceiver {
    String alram_message_string;
    int chatting_room_key;
    String product_key;
    String id;
    String date_to_string;

    String channelName ="당근 마켓 약속 알림";
    String description;


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notification_intenet;

            notification_intenet=new Intent(context, SplashActivity.class);







        alram_message_string=intent.getStringExtra("alram_message_json");

        Log.e("alram_message_string",""+alram_message_string);

        JSONObject alram_message_json= null;
        try {
            alram_message_json = new JSONObject(alram_message_string);
            JSONObject alram_message=new JSONObject(alram_message_json.getString("message"));

            //상대편 아이디로 바꾸기
            if (alram_message_json.getString("id").equals(new UserInfoSave(context).return_account().getId())){
                id=alram_message_json.getString("opponent");
            }else {
                id=alram_message_json.getString("id");
            }


            description = id+"님과 약속시간 "+alram_message.getString("alram_time_min")+"분 전 입니다.";
            product_key=alram_message_json.getString("product_key");
            date_to_string=alram_message_json.getString("message");

            Log.e("alram_message_json",""+alram_message_json);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        intent.putExtra("product_key",product_key);
        intent.putExtra("id",id);


        Log.e("alram_reciever","??"+product_key+"??"+alram_message_string);



        //product_key
        //id
        //opponent
        //date


        notification_intenet.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent=getActivity(context, 0,notification_intenet,FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"default");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            channelName ="당근 마켓 약속 알림";



            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder.setAutoCancel(true)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(channelName)
                .setContentText(description)
                .setContentInfo("INFO")
                .setContentIntent(pendingIntent);



        if (notificationManager != null) {

            // 노티피케이션 동작시킴

              //노티 id chatting_room_key
            notificationManager.notify(chatting_room_key, builder.build());
            //노티피케이션 캔슬
            notificationManager.cancel(1235);


        }



    }




    private String convert_time_value(int time_value_int){
        String time_value = null;
        int hour,min;
        hour=time_value_int/60;
        min=time_value_int%60;

        if (hour==0){
            time_value=min+"분 전 입니다.";

        }else if (min==0){
            time_value=hour+"시간 전 입니다.";
        }
        else {
            time_value=hour+"시"+min+"분 전 입니다.";
        }

        return time_value;

    }




}
