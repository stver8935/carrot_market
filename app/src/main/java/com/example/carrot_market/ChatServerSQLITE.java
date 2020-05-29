package com.example.carrot_market;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatServerSQLITE extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="DeedReader.db;";

    public ChatServerSQLITE(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_query
                ="CREATE TABLE USER_CHANNEL (User_Key INTEGER PRIMARY KEY," +
                "User_Id TEXT," +
                "User_Channel TEXT)";

        //데이터베이스 생성 쿼리

        db.execSQL(create_table_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query="DROP TABLE USER_CHANNEL";
        db.execSQL(drop_query);
        onCreate(db);
    }
}
