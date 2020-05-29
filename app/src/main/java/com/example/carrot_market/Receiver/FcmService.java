package com.example.carrot_market.Receiver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.CONTROLLER.SplashActivity;
import com.example.carrot_market.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FcmService extends FirebaseMessagingService {

    String id = "my_channel_02";
    CharSequence name = "fcm_nt";
    String description = "push";
    int importance = NotificationManager.IMPORTANCE_LOW;
    MediaPlayer mediaPlayer;
    Bitmap bigPicture;
    String message_txet=null;
    String CHANNEL_ID = "my_channel_02";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }




        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage)  {

        //알람 소리 설정
            mediaPlayer = MediaPlayer.create(this, R.raw.notificaiton_call);
            mediaPlayer.start();

            //받은 메시지의 내용
        Log.e("fcm_remote_data",""+remoteMessage.getData().toString());
                //fcm_remote_date
                //json 구조
                //product_key
                //id-- 보낸사람 아이디
                //message
                //--date_format -약속 설정한 시간
                //alram_time_min 약속된 시간으로 부터 몇분전에 알람을 받을것인지
                //message_type 메시지의 타입
                //--0 약속 시간 설정 메시지
                //--1 일반 메시지

                //--2 채팅방 나가기 메시지
                //채팅방 나가기는 받지 않는다.
           
            try {
            if (remoteMessage.getNotification() != null) { //포그라운드
                Log.e("message_back","back");
                    sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),1);
            }
            else if (remoteMessage.getData().size() > 0) { //백그라운드
                Log.e("message_fore","fore");
                sendNotification(remoteMessage.getData().get("body"),remoteMessage.getData().get("title"),0);
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private void sendNotification(String messageBody, String messageTitle,int fore_back) throws JSONException {


            //////////////////////////// 포그라운드 및 백그라운드 푸시알림 처리 ////////////////////////////

            String title=messageTitle;
            JSONObject message_json=new JSONObject(messageBody);


            //메시지 클릭시 이동할 액티비티
            //액티비티가 포어 그라운드 상태일때 ==0
            //백그라운드 상태 일때 ==1


            //포어그라운드
            if (fore_back==0) {
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra("chatting_check", 4);
                intent.putExtra("product_key",message_json.getString("product_key"));


//            intent.putExtra("message_Body",messageBody);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)(System.currentTimeMillis()/1000)/* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);

                mChannel.setDescription(description);
                mChannel.enableLights(true);
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                try {
                    Notification notification = new Notification.Builder(FcmService.this)
                            .setContentTitle(URLDecoder.decode(messageTitle, "UTF-8"))
                            .setContentText(message_json.getString("title")+" "+message_json.getString("message"))
                            .setSmallIcon(R.drawable.apps_icon)
                            .setAutoCancel(true)
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setStyle(new Notification.BigTextStyle())
                            .build();

                    //중간에 오는 시스템 시간을 이용한 int 값은 채널 아이디와 product_key 로 만들기


                    mNotificationManager.notify((int)System.currentTimeMillis(), notification);



                } catch (Exception e) {
                    e.printStackTrace();
                }

                //백그라운드
            }else {

                Intent intent = new Intent(this, Product.class);
                intent.putExtra("product_key",message_json.getString("product_key"));


//            intent.putExtra("message_Body",messageBody);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)(System.currentTimeMillis()/1000)/* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);

                mChannel.setDescription(description);
                mChannel.enableLights(true);
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                try {
                    Notification notification = new Notification.Builder(FcmService.this)
                            .setContentTitle(URLDecoder.decode(messageTitle, "UTF-8"))
                            .setContentText(message_json.getString("title")+" "+message_json.getString("message"))
                            .setSmallIcon(R.drawable.apps_icon)
                            .setAutoCancel(true)
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setStyle(new Notification.BigTextStyle())
                            .build();

                    //중간에 오는 시스템 시간을 이용한 int 값은 채널 아이디와 product_key 로 만들기


                    mNotificationManager.notify((int)System.currentTimeMillis(), notification);



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            //////////////////////////// 포그라운드 및 백그라운드 푸시알림 처리 ////////////////////////////


        }

        //약속 알림 메시지일 경우에 예약 알림을 요청하는 메서드
    public void reservation_alarm(JSONObject message_body) throws JSONException {

        //예약 알림을 위한 워크 매니저
//        androidx.work.WorkManager workManager = androidx.work.WorkManager.getInstance();
//        Data worker_manager_data = new Data.Builder()
//                .putString("message", ""+message_body)
//                .build();


        JSONObject alram_json_body=new JSONObject(message_body.getString("message")) ;

        try {

            String promise_date_string=alram_json_body.getString("date_format");
            int alram_time_min=alram_json_body.getInt("alram_time_min")*1000*60;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long now = System.currentTimeMillis();

            //현재시간
            Date now_date = new Date(now);
            //약속된 시간
            Date promise_date = format.parse(promise_date_string);
            //알림받을 시간
            long date=promise_date.getTime()-now_date.getTime()-alram_time_min;
            long sub_date=promise_date.getTime()-now_date.getTime();




//            OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(WorkManager.class)
//                    .setInitialDelay(date/1000, TimeUnit.SECONDS)
//                    .setInputData(worker_manager_data)
//                    .build();
//            workManager.getInstance(getApplicationContext()).enqueue(uploadWorkRequest);


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    private boolean isAppRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            if(procInfos.get(i).processName.equals(context.getPackageName())){
                return true;
            }
        }

        return false;
    }

}
