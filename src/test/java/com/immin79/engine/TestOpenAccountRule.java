package com.immin79.engine;

import com.immin79.engine.impl.OpenAccountRule;
import com.immin79.model.log.BaseUserLog;
import com.immin79.model.log.DepositUserLog;
import com.immin79.model.log.OpenAccountUserLog;
import com.immin79.model.log.WithdrawUserLog;
import com.immin79.util.DtfZonedDateTimeUtil;
import com.immin79.util.ZonedDateTimeUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class TestOpenAccountRule {

    private static List<BaseUserLog> baseUserLogList;
    private static ZonedDateTime openAccountDate;

    /**
     * 계좌 log 생성
     *
     * - 2017-01-01 10:00:00 계좌 생성
     * - 2017-07-05 10:00:00 90만원 입금
     * - 2017-07-05 10:05:00 1만원 입금
     * - 2017-07-05 11:00:00 90만원 출금
     */
    @BeforeClass
    public static void init() {

        openAccountDate = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:00:00");

        long customerId = 1122;
        String accountNum = "123-123-123";

        baseUserLogList = new ArrayList<>();

        baseUserLogList.add(new OpenAccountUserLog(openAccountDate, customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new WithdrawUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("900000")));
    }

    /**
     * 1일 이내 계좌 생성을 한 경우
     */
    @Test
    public void testEvaluate1() {

        int withinDays = 1;

        OpenAccountRule rule = new OpenAccountRule(withinDays);

        boolean result = rule.evaluate(baseUserLogList);
        Assert.assertFalse(result);
    }

    /**
     * 100일 이내 계좌 생성을 한 경우
     */
    @Test
    public void testEvaluate2() {

        int withinDays = 100;

        OpenAccountRule rule = new OpenAccountRule(withinDays);

        boolean result = rule.evaluate(baseUserLogList);

        if(ZonedDateTimeUtil.dayDiff(openAccountDate, ZonedDateTime.now(ZoneId.of("Asia/Seoul"))) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);
    }

    /**
     * 1000일 이내 계좌 생성을 한 경우
     */
    @Test
    public void testEvaluate3() {

        int withinDays = 1000;

        OpenAccountRule rule = new OpenAccountRule(withinDays);

        boolean result = rule.evaluate(baseUserLogList);

        if(ZonedDateTimeUtil.dayDiff(openAccountDate, ZonedDateTime.now(ZoneId.of("Asia/Seoul"))) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);
    }

    /**
     * User Log 가 없는 경우
     */
    @Test
    public void testEvaluate4() {

        int withinDays = 1000;

        OpenAccountRule rule = new OpenAccountRule(withinDays);

        boolean result = rule.evaluate(null);

        Assert.assertFalse(result);
    }
}
