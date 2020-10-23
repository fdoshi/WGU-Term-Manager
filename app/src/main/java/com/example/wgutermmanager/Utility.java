package com.example.wgutermmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utility {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean checkDate(String value) {
        Date date = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null;
    }
//getDateTimeStamp
    public static long getTimeInMSec(String dateInput) {
        try {
            Date date = Utility.sdf.parse(dateInput + TimeZone.getDefault().getDisplayName());
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dateInput= Long.toString(c.getTimeInMillis());
            long returndate = Long.parseLong(dateInput);
            return returndate;
        }
        catch (ParseException e) {
            return 0;
        }
    }
}
