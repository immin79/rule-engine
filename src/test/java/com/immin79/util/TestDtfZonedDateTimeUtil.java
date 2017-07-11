package com.immin79.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

/**
 * Created by bryan.79 on 2017. 7. 11..
 */
public class TestDtfZonedDateTimeUtil {

    @Test
    public void testConvertZonedDateTime() {

        ZonedDateTime zonedDateTime = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:10:10");

        Assert.assertEquals(2017, zonedDateTime.getYear());

        Assert.assertEquals(7, zonedDateTime.getMonthValue());
        Assert.assertEquals(1, zonedDateTime.getDayOfMonth());
        Assert.assertEquals(10, zonedDateTime.getHour());
        Assert.assertEquals(10, zonedDateTime.getMinute());
        Assert.assertEquals(10, zonedDateTime.getSecond());
    }
}
