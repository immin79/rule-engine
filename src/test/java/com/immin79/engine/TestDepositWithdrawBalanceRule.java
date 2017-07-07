package com.immin79.engine;

import com.immin79.engine.impl.DepositWithdrawBalanceRule;
import com.immin79.model.log.BaseUserLog;
import com.immin79.model.log.DepositUserLog;
import com.immin79.model.log.OpenAccountUserLog;
import com.immin79.model.log.WithdrawUserLog;
import com.immin79.util.SdfDateUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class TestDepositWithdrawBalanceRule {

    private static List<BaseUserLog> baseUserLogList;

    @BeforeClass
    public static void init() {

        long customerId = 1111;
        String accountNum = "123-123-123";

        baseUserLogList = new ArrayList<BaseUserLog>();

        baseUserLogList.add(new OpenAccountUserLog(SdfDateUtil.convertDate("2017-07-01 10:00:00"), customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(SdfDateUtil.convertDate("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new WithdrawUserLog(SdfDateUtil.convertDate("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("900000")));
    }

    @Test
    public void testEvaluate1() {

        BigDecimal gteDepositAmount = new BigDecimal("900000");
        BigDecimal lteDepositAmount = new BigDecimal("1000000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();
        Assert.assertTrue(result);
    }

    @Test
    public void testEvaluate2() {

        BigDecimal gteDepositAmount = new BigDecimal("1000000");
        BigDecimal lteDepositAmount = new BigDecimal("1200000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();
        Assert.assertFalse(result);
    }


    @Test
    public void testEvaluate3() {

        BigDecimal gteDepositAmount = new BigDecimal("900000");
        BigDecimal lteDepositAmount = new BigDecimal("950000");
        long withinHoursAfterDeposit = 10;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();
        Assert.assertTrue(result);
    }


    @Test
    public void testEvaluate4() {

        BigDecimal gteDepositAmount = new BigDecimal("600000");
        BigDecimal lteDepositAmount = new BigDecimal("900000");
        long withinHoursAfterDeposit = 1;
        BigDecimal lteBalance = new BigDecimal("10000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);
        rule.setBaseUserLogList(baseUserLogList);


        boolean result = rule.evaluate();
        Assert.assertTrue(result);
    }

}
