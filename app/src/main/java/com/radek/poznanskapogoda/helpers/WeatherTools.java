package com.radek.poznanskapogoda.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Radek on 15.01.2017.
 */

public abstract class WeatherTools {

    public static String getDate(long timeStamp){
        //// TODO: 15.01.2017 magic number and string resources
        try{
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date netDate = (new Date(timeStamp * 1000L));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return ex.getMessage();
        }
    }
}
