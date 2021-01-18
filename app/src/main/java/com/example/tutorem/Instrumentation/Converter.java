package com.example.tutorem.Instrumentation;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Converter {
    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
    public static Date stringToDate(String string){
        Date date = new Date();
        try {
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            date = dateFormat.parse(string);
        }catch (Exception e){
            Log.d("CONVERTER","Could not convert String to Date");
        }
        return date;
    }
    public static String dateToReadableString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
}
