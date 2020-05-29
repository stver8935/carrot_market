package com.example.carrot_market.CONTROLLER.METHOD;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    String time_stamp;

    public DateTimeConverter(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String return_month_day() throws ParseException {

        Date now_date=new Date(System.currentTimeMillis());
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time_stamp);

        Log.e("convert_date",""+date);

        SimpleDateFormat view_date_format = new SimpleDateFormat("MM 월 dd 일 HH 시 mm 분");
        return view_date_format.format(date);
    }

    public String return_yaer_month_day() throws ParseException {
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time_stamp);

        SimpleDateFormat simpleDateFormat_ymd=new SimpleDateFormat("yyyy 년 MM 월 dd 일");

        return simpleDateFormat_ymd.format(date);
    }

    public String return_hour_min() throws ParseException {
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time_stamp);
        date.getHours();

        String before_after_noon;

        if (12<date.getHours()){
                date.setHours(date.getHours()-12);
            before_after_noon="오후";
        }else {
               before_after_noon="오전";
        }
        SimpleDateFormat simpleDateFormat_hm=new SimpleDateFormat(before_after_noon+"HH:mm");

        return simpleDateFormat_hm.format(date);
    }
}
