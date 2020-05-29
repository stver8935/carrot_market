package com.example.carrot_market.MODEL.DTO;

import java.util.Calendar;
import java.util.Date;

public class DateTimeItem {
    Calendar cal;

    int year;
    int month;
    int day;
    int hour;
    int min;
    String date_time_string;

    public DateTimeItem(Calendar cal) {
        this.cal=cal;
        Date date=cal.getTime();
        date.setMonth(cal.get(Calendar.MONTH));
        cal.setTime(date);
    }


    public Date return_date_time(){
        Date date=cal.getTime();
        return date;
    }

    public Calendar return_calendar(){
        return cal;
    }

    public String return_to_string(){

        String year=String.valueOf(cal.get(Calendar.YEAR));
        String mon= String.valueOf(cal.get(Calendar.MONTH)+1);
        String day_string=String.valueOf(cal.get(Calendar.DATE));
        String hour_strig=String.valueOf(cal.get(Calendar.HOUR));
        String min_string=String.valueOf(cal.get(Calendar.MINUTE));


        if (mon.length()<2){
            mon="0"+mon;
        }

        if (day_string.length()<2) {
            day_string="0"+day_string;
        }
        if (hour_strig.length()<2){
            hour_strig="0"+hour_strig;
        }
        if (min_string.length()<2){
            min_string="0"+min_string;
        }

        date_time_string = "" + year+ "년" + mon + "월" +day_string+"일"+hour_strig+":"+min_string;

        return date_time_string;
    }


    public int getYear() {
        return cal.get(Calendar.YEAR);
    }

    public void setYear(int year) {

        cal.set(Calendar.YEAR,year);

    }

    public int getMonth() {
        return cal.get(Calendar.MONTH);
    }

    public void setMonth(int month)
    {
        cal.set(Calendar.MONTH,month);

    }

    public int getDay() {
        return cal.get(Calendar.DATE);
    }

    public void setDay(int date) {
        cal.set(Calendar.DATE,date);

    }

    public int getHour() {
        return cal.get(Calendar.HOUR);
    }

    public void setHour(int hour) {
        cal.set(Calendar.HOUR,hour);

    }

    public int getMin() {
        return cal.get(Calendar.MINUTE);
    }

    public void setMin(int min) {
        cal.set(Calendar.MINUTE,min);
    }






}
