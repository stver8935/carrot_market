package com.example.carrot_market.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.example.carrot_market.CONTROLLER.Chatting;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ChattingReceiver extends BroadcastReceiver {
    private Context context;

    private String name;
    private String my_name;
    private String product_key;
    private String message;
    private NotificationCompat.Builder builder;


    @Override
    public void onReceive(Context context, Intent intent) {


         name=intent.getStringExtra("name");
        my_name=intent.getStringExtra("my_name");
        product_key=intent.getStringExtra("product_key");
        message=intent.getStringExtra("message");



        Object message_json = null;
        try {
            message_json = new JSONTokener(message).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (message_json instanceof JSONObject)
        {
        message=name+" 님이 거래약속을 만드셨습니다";
        }

        String channelId = "channel";
        String channelName = "Channel Name";

        NotificationManager notifManager

                = (NotificationManager) context.getSystemService  (Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            notifManager.createNotificationChannel(mChannel);

        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId);

        Intent notificationIntent = new Intent(context
                , Chatting.class);
        notificationIntent.putExtra("product_key",product_key);
        notificationIntent.putExtra("id",name);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1234, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle(name) // required
                .setContentText(message)  // required
                .setDefaults(Notification.DEFAULT_ALL) // 알림, 사운드 진동 설정
                .setAutoCancel(true) // 알림 터치시 반응 후 삭제
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentIntent(pendingIntent);

        notifManager.notify(0, builder.build());

    }
}
