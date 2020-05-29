package com.example.carrot_market.MODEL.LOCALMODEL.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProductNotificaitonDatabase {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    public ProductNotificaitonDatabase(Context context) {
        this.context=context;
        sqLiteDatabase=context.openOrCreateDatabase("product_notificaiton_catabase",Context.MODE_PRIVATE,null);
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+"product_notificaiton_database"+
//                "(product_key VARCHAR(30), )");
//
    }
}
