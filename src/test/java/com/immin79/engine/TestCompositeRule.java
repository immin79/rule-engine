package com.immin79.engine;

import com.immin79.engine.impl.DepositWithdrawBalanceRule;
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
public class TestCompositeRule {

    private static List<BaseUserLog> baseUserLogList;

    private static Date openAccountDate;

    @BeforeClass
    public static void init() {

        openAccountDate = SdfDateUtil.convertDate("2017-07-01 10:00:00");

        long customerId = 2222;
        String accountNum = "123-123-123";

        baseUserLogList = new ArrayList<BaseUserLog>();

        baseUserLogList.add(new OpenAccountUserLog(openAccountDate, customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new WithdrawUserLog(SdfDateUtil.convertDate("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("900000")));
    }

    @Test
    public void testEvaluate1() {

        /**
         * OpenAccount Rule setting
         */
        int withinDays = 1000;
        OpenAccountRule openAccountRule = new OpenAccountRule(withinDays);

        /**
         * DepositWithdrawBalance Rule setting
         */
        BigDecimal gteDepositAmount = new BigDecimal("900000");
        BigDecimal lteDepositAmount = new BigDecimal("1000000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");

        DepositWithdrawBalanceRule depositWithdrawBalanceRule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        /**
         * Final step : composite Rule Generation
         */
        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate();

        if(DateUtil.dayDiff(openAccountDate, new Date()) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);

    }

    @Test
    public void testEvaluate2() {

        /**
         * OpenAccount Rule setting
         */
        int withinDays = 1;
        OpenAccountRule openAccountRule = new OpenAccountRule(withinDays);

        /**
         * DepositWithdrawBalance Rule setting
         */
        BigDecimal gteDepositAmount = new BigDecimal("800000");
        BigDecimal lteDepositAmount = new BigDecimal("1000000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");

        DepositWithdrawBalanceRule depositWithdrawBalanceRule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        /**
         * Final step : composite Rule Generation
         */
        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate();

        Assert.assertFalse(result);

    }

    @Test
    public void testEvaluate3() {

        /**
         * OpenAccount Rule setting
         */
        int withinDays = 3650;
        OpenAccountRule openAccountRule = new OpenAccountRule(withinDays);

        /**
         * DepositWithdrawBalance Rule setting
         */
        BigDecimal gteDepositAmount = new BigDecimal("2000000");
        BigDecimal lteDepositAmount = new BigDecimal("10000000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");

        DepositWithdrawBalanceRule depositWithdrawBalanceRule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        /**
         * Final step : composite Rule Generation
         */
        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate();

        Assert.assertFalse(result);

    }

}
