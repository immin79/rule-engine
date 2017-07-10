package com.immin79.engine;

import com.immin79.engine.impl.DepositWithdrawBalanceRule;
import com.immin79.model.log.*;
import com.immin79.util.DtfZonedDateTimeUtil;
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

    /**
     * 계좌 log 생성
     *
     * - 2017-01-01 10:00:00 계좌 생성
     * - 2017-07-05 10:00:00 90만원 입금
     * - 2017-07-05 10:05:00 1만원 입금
     * - 2017-07-05 10:55:00 10만원 이체
     * - 2017-07-05 11:00:00 80만원 출금
     */
    @BeforeClass
    public static void init() {

        long customerId = 1111;
        String accountNum = "123-123-123";

        // SendUserLog Info
        long receivingBank = 123456;
        String receivingAccountName = "recievingAccountName";

        baseUserLogList = new ArrayList<>();

        baseUserLogList.add(new OpenAccountUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:00:00"), customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new SendUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:55:00"), customerId, accountNum, new BigDecimal("910000"), receivingBank, receivingAccountName, new BigDecimal("100000")));
        baseUserLogList.add(new WithdrawUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("800000")));
    }

    @Test
    public void testEvaluate1() {

        BigDecimal gteDepositAmount = new BigDecimal("900000");
        BigDecimal lteDepositAmount = new BigDecimal("1000000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);


        boolean result = rule.evaluate(baseUserLogList);
        Assert.assertTrue(result);
    }

    @Test
    public void testEvaluate2() {

        BigDecimal gteDepositAmount = new BigDecimal("1000000");
        BigDecimal lteDepositAmount = new BigDecimal("1200000");
        long withinHoursAfterDeposit = 2;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        boolean result = rule.evaluate(baseUserLogList);
        Assert.assertFalse(result);
    }


    @Test
    public void testEvaluate3() {

        BigDecimal gteDepositAmount = new BigDecimal("900000");
        BigDecimal lteDepositAmount = new BigDecimal("950000");
        long withinHoursAfterDeposit = 10;
        BigDecimal lteBalance = new BigDecimal("20000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        boolean result = rule.evaluate(baseUserLogList);
        Assert.assertTrue(result);
    }


    @Test
    public void testEvaluate4() {

        BigDecimal gteDepositAmount = new BigDecimal("600000");
        BigDecimal lteDepositAmount = new BigDecimal("900000");
        long withinHoursAfterDeposit = 1;
        BigDecimal lteBalance = new BigDecimal("10000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        boolean result = rule.evaluate(baseUserLogList);
        Assert.assertTrue(result);
    }

    @Test
    public void testEvaluate5() {

        BigDecimal gteDepositAmount = new BigDecimal("600000");
        BigDecimal lteDepositAmount = new BigDecimal("900000");
        long withinHoursAfterDeposit = 1;
        BigDecimal lteBalance = new BigDecimal("10000");


        DepositWithdrawBalanceRule rule = new DepositWithdrawBalanceRule(gteDepositAmount, lteDepositAmount, withinHoursAfterDeposit, lteBalance);

        /**
         * User log list is null
         */
        boolean result = rule.evaluate(null);
        Assert.assertFalse(result);
    }
}
