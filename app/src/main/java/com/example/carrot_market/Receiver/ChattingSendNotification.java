package com.example.carrot_market.Receiver;

import android.content.Context;

import androidx.core.app.NotificationCompat;

public class ChattingSendNotification {
    private Context context;

    private String name;
    private String my_name;
    private String product_key;
    private String message;

    private NotificationCompat.Builder builder;

    public ChattingSendNotification(Context context, String name,
                                    String my_name, String product_key,
                                    String message) {
        this.context = context;
        this.name = name;
        this.my_name = my_name;
        this.product_key = product_key;
        this.message=message;
    }





}
