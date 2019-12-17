package com.example.carrot_market.MODEL.LOCALMODEL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE CHAT_LIST(ID TEXT,COMENT TEXT)");

    }


    //데이터베이스 수정 부분
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //데이터베이스 읽기 부분
    public String getResult(){
     SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT *FROM CHAT_LIST",null);


     while (cursor.moveToNext()){
         String result=cursor.getString(0)
                 +","
                 +cursor.getString(1);

         return result;
     }
        return null;
    }


    public void insert(String ic,String coment){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("ID",ic);
        values.put("COMENT",coment);

        db.insert("CHAT_LIST",null,values);

    }


}
