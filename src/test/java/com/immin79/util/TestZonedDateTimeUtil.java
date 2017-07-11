package com.immin79.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

/**
 * Created by bryan.79 on 2017. 7. 11..
 */
public class TestZonedDateTimeUtil {

    @Test
    public void testDayDiff1() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:50:10");

        long dayDiff = ZonedDateTimeUtil.dayDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(0, dayDiff);
    }

    @Test
    public void testDayDiff2() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-11 10:10:10");

        long dayDiff = ZonedDateTimeUtil.dayDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(10, dayDiff);
    }

    @Test
    public void testDayDiff3() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-11 10:10:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");

        long dayDiff = ZonedDateTimeUtil.dayDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(10, dayDiff);
    }

    @Test
    public void testDayDiff4() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2016-07-01 10:10:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");

        long dayDiff = ZonedDateTimeUtil.dayDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(365, dayDiff);
    }

    @Test
    public void testHourDiff1() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 13:05:10");

        long hourDiff = ZonedDateTimeUtil.hourDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(2, hourDiff);
    }

    @Test
    public void testHourDiff2() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 13:05:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");

        long hourDiff = ZonedDateTimeUtil.hourDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(2, hourDiff);
    }

    @Test
    public void testHourDiff3() {

        ZonedDateTime zonedDateTime1 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-02 13:05:10");
        ZonedDateTime zonedDateTime2 = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");

        long hourDiff = ZonedDateTimeUtil.hourDiff(zonedDateTime1, zonedDateTime2);
        Assert.assertEquals(26, hourDiff);
    }
}
