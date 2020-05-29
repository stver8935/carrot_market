package com.example.carrot_market.CONTROLLER.METHOD;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.carrot_market.CONTROLLER.MainFragment;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WorkManager extends Worker {
    private Context context;
    private WorkerParameters parameters;

    public WorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        parameters=workerParams;
        this.context=context;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        Data data=parameters.getInputData();

        JSONObject jsonObject= null;
        JSONObject promise_json=null;

        try {
            jsonObject = new JSONObject(data.getString("message"));

           promise_json=new JSONObject(jsonObject.getString("message"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        CharSequence name = "deal_promise_alarm";
        int importance = NotificationManager.IMPORTANCE_LOW;

        Intent alramIntent=new Intent(getApplicationContext(), MainFragment.class);
        alramIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        alramIntent.putExtra("chatting_check",1);
        try {
            alramIntent.putExtra("product_key",jsonObject.getString("product_key"));
            alramIntent.putExtra("id",jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, alramIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //플래그의 의미는 한번판 알려주고고


        try {




            Log.e("work_data",""+jsonObject);
            Log.e("work_data_alram",""+promise_json);

            NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel("dd", name, importance);

            mChannel.setDescription("description");
            mChannel.enableLights(true);



            mNotificationManager.createNotificationChannel(mChannel);

            mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = null;




            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(URLDecoder.decode("당근마켓 약속 알림", "UTF-8"))
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.apps_icon)
                    .setContentIntent(pendingIntent)
                    .setChannelId(mChannel.getId())
                    .setContentText(jsonObject.getString("id")+"님과 약속시간"+promise_json.getInt("alram_time_min")+"분 전 입니다.")
                    .setStyle(new Notification.BigTextStyle()
                            .bigText(jsonObject.getString("id")+"님과 약속시간"+promise_json.getInt("alram_time_min")+"분 전 입니다."))
                    .build();

            mNotificationManager.notify((int)(System.currentTimeMillis()/1000), notification);

        }catch (UnsupportedEncodingException | JSONException e) {

            e.printStackTrace();
        }


        return Result.success();
    }
}
