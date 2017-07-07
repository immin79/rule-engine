package com.kakaobank.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class DateUtil {

    /**
     * Day 차이값을 반환한다
     *
     * @param date1 특정일1
     * @param date2 특정일2
     * @return 두 날짜간 Day 차이값
     */
    public static long dayDiff(Date date1, Date date2) {

        long timeDiff = Math.abs(date1.getTime() - date2.getTime());

        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


    /**
     * Hour 차이값을 반환한다
     *
     * @param date1 특정일1
     * @param date2 특정일2
     * @return 두 날짜간 Hour 차이값
     */
    public static long hourDiff(Date date1, Date date2) {

        long timeDiff = Math.abs(date1.getTime() - date2.getTime());

        return TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


}
