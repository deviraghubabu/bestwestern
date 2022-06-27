package com.bw.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static synchronized String currentDate() throws Exception {
        String currentDate = null;
        Date todayDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        currentDate = formatter.format(todayDate);
       

        return currentDate;
    }

    public static synchronized String currentDateWithFormat(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String stepTime = sdf.format(date);
        return stepTime;
    }

    public static synchronized Long getCurrentTimeInUnixFormat() {
        return (new Date().getTime()/1000L);
    }

    public static synchronized long getTheUnixTimeForEDT(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy hh:mm:ss a");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = dateFormat.parse(dateString);
        long unixTime = date.getTime() / 1000L;
        return unixTime;
    }

    public static synchronized long getTheUnixTimeForEDT(String format, String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = dateFormat.parse(dateString);
        long unixTime = date.getTime() / 1000L;
        return unixTime;
    }
}
