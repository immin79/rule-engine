package com.immin79.util;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by bryan.79 on 2017. 7. 10..
 */
public class ZonedDateTimeUtil {

    /**
     * Day 차이값을 반환한다
     *
     * @param date1 특정일1
     * @param date2 특정일2
     * @return 두 날짜간 Day 차이값
     */
    public static long dayDiff(ZonedDateTime date1, ZonedDateTime date2) {

        Period period = Period.between(date1.toLocalDate(), date2.toLocalDate());

        return Math.abs(period.getDays());
    }


    /**
     * Hour 차이값을 반환한다
     *
     * @param date1 특정일1
     * @param date2 특정일2
     * @return 두 날짜간 Hour 차이값
     */
    public static long hourDiff(ZonedDateTime date1, ZonedDateTime date2) {

        long timeDiff = Math.abs(ChronoUnit.MILLIS.between(date1, date2));

        return TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
}
