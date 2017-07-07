package com.kakaobank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bryan.79 on 2017. 7. 8..
 */
public class SdfDateUtil {

    public static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date convertDate(String dateStr) {

        Date date = null;

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
