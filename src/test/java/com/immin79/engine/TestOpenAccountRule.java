package com.immin79.engine;

import com.immin79.engine.impl.OpenAccountRule;
import com.immin79.model.log.BaseUserLog;
import com.immin79.model.log.DepositUserLog;
import com.immin79.model.log.OpenAccountUserLog;
import com.immin79.model.log.WithdrawUserLog;
import com.immin79.util.DateUtil;
import com.immin79.util.SdfDateUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class TestOpenAccountRule {

    private static List<BaseUserLog> baseUserLogList;
    private static Date openAccountDate;

    @BeforeClass
    public static void init() {

        openAccountDate = SdfDateUtil.convertDate("2017-07-01 10:00:00");

        long customerId = 1122;
        String accountNum = "123-123-123";

        baseUserLogList = new ArrayList<BaseUserLog>();

        baseUserLogList.add(new OpenAccountUserLog(openAccountDate, customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new WithdrawUserLog(SdfDateUtil.convertDate("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("900000")));
    }

    @Test
    public void testEvaluate1() {

        int withinDays = 1;

        OpenAccountRule rule = new OpenAccountRule(withinDays);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();
        Assert.assertFalse(result);
    }

    @Test
    public void testEvaluate2() {

        int withinDays = 100;

        OpenAccountRule rule = new OpenAccountRule(withinDays);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();

        if(DateUtil.dayDiff(openAccountDate, new Date()) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);
    }


    @Test
    public void testEvaluate3() {

        int withinDays = 1000;

        OpenAccountRule rule = new OpenAccountRule(withinDays);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();

        if(DateUtil.dayDiff(openAccountDate, new Date()) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);
    }
}
