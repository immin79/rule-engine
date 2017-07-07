package com.kakaobank.engine;

import com.kakaobank.engine.impl.DepositWithdrawBalanceRule;
import com.kakaobank.engine.impl.OpenAccountRule;
import com.kakaobank.model.log.BaseUserLog;
import com.kakaobank.model.log.DepositUserLog;
import com.kakaobank.model.log.OpenAccountUserLog;
import com.kakaobank.model.log.WithdrawUserLog;
import com.kakaobank.util.DateUtil;
import com.kakaobank.util.SdfDateUtil;
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


        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

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


        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

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


        CompositeRule compositeRule = new CompositeRule();
        compositeRule.add(openAccountRule);
        compositeRule.add(depositWithdrawBalanceRule);

        compositeRule.setBaseUserLogList(baseUserLogList);

        boolean result = compositeRule.evaluate();

        Assert.assertFalse(result);

    }

}
