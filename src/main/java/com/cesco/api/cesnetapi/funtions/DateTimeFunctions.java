package com.cesco.api.cesnetapi.funtions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFunctions {
    
    public static Timestamp getTimestamp(String timeStr, String format) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return new Timestamp(formatter.parse(timeStr).getTime());
    }

    public static String getTimestampStr(Timestamp time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(time);
    }

    public static Date getTime(String timeStr, String format) {

        if (timeStr == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Date date;

        try {
            date = formatter.parse(timeStr);
        } catch (ParseException e) {

            return null;
        }

        return date;
    }

    public static String getTimeStr(Date time, String format) {
        
        if (time == null) {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format); 
        return formatter.format(time);
    }

    public static String setTimeDash(String timeStr, String originFormat, String returnFormat) {

        if ("".equals(timeStr)) {
            return timeStr;
        }

        Date date = getTime(timeStr, originFormat);
        return getTimeStr(date, returnFormat);
    }
}
