package com.example.carrot_market.CONTROLLER.METHOD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {

    private String insert_date;
    private String insert_min;

    public StringToDate(String insert_date, String insert_min) {
        this.insert_date = insert_date;
        this.insert_min = insert_min;
    }



    public Date natural_convert(){
        Date date = null;
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date = transFormat.parse(insert_date);
             date.setMinutes(-Integer.parseInt(insert_min));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
