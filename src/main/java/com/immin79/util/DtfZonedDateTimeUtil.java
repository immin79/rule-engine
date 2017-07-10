package com.immin79.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by bryan.79 on 2017. 7. 10..
 */
public class DtfZonedDateTimeUtil {

    public static void main(String[] args) {

        String dateStr = "2017-07-10 10:21:00";

        System.out.println(convertZonedDateTime(dateStr));
    }

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ZonedDateTime convertZonedDateTime(String dateStr) {

        return ZonedDateTime.parse(dateStr, dtf.withZone(ZoneId.systemDefault()));
    }
}
