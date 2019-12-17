package com.example.carrot_market.MODEL.CHATTINGSERVICE;

import android.Manifest;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.carrot_market.R;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class ChattingService extends Service{
    public static final int MSG_REGISTER_CLIENT=1;
    private static final int MSG_UNREGISTER_CLIENT=2;
    private static final int MSG_SEND_TO_SERVICE=3;
    private static final int MSE_SEND_TO_ACTIVITY=4;

    private Messenger mClient=null;
    static final String HOST = System.getProperty("host", "192.168.1.108");
    static final int PORT = Integer.parseInt(System.getProperty("port", "3306"));
    static Channel socketchannel;
     Handler handler;

    public ChattingService() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
 final Context context=getApplicationContext();

     //스레드 구현
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final SslContext sslCtx = SslContextBuilder.forClient()
                            .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

                    EventLoopGroup group=new NioEventLoopGroup();
                    Bootstrap bootstrap=new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new ChatClientinitializer(sslCtx,getApplicationContext()));
                    socketchannel=bootstrap.connect(HOST,PORT).sync().channel();


                } catch (Exception ioe) {
                    Log.e("Servser_Error", ioe.getMessage() + "입니다");
                    ioe.printStackTrace();

                }




                if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                LocationManager locationManager = (LocationManager) getApplication().getSystemService(getApplication().LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



            }
        }).start();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private final Messenger mMessenger=new Messenger(new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {


        return false;
    }
}));




    public static class SendmsgTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {

            try {
                ChannelFuture lastWriteFuture = null;
                lastWriteFuture = socketchannel.writeAndFlush(strings[0]+ "\r\n");
                lastWriteFuture.sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
        }
    }

}
