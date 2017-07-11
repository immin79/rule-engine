package com.immin79.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by bryan.79 on 2017. 7. 10..
 */
public class DtfZonedDateTimeUtil {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss 포맷의 날짜 스트링
     * @return ZoendDateTime Type의 날짜
     */
    public static ZonedDateTime convertZonedDateTime(String dateStr) {

        return ZonedDateTime.parse(dateStr, dtf.withZone(ZoneId.systemDefault()));
    }
}
