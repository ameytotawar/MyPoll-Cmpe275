package com.poll.util;

import com.poll.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

//    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    /**
     * @param dateString
     * @return
     */
    public static Date getDate(String dateString, String formatString){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDate(String dateString){
        return getDate(dateString, DATE_FORMAT);
    }

    public static String getDateString(Date date){
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(date);
        return dateString;
    }


}
