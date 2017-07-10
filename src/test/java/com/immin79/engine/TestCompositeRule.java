package com.immin79.engine;

import com.immin79.engine.impl.DepositWithdrawBalanceRule;
import com.immin79.engine.impl.OpenAccountRule;
import com.immin79.model.log.*;
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
public class TestCompositeRule {

    private static List<BaseUserLog> baseUserLogList;

    private static ZonedDateTime openAccountDate;

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

        openAccountDate = DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-01 10:00:00");

        long customerId = 2222;
        String accountNum = "123-123-123";

        // SendUserLog Info
        long receivingBank = 123456;
        String receivingAccountName = "recievingAccountName";

        baseUserLogList = new ArrayList<>();

        baseUserLogList.add(new OpenAccountUserLog(openAccountDate, customerId, accountNum));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:00:00"), customerId, accountNum, new BigDecimal("900000")));
        baseUserLogList.add(new DepositUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:05:00"), customerId, accountNum, new BigDecimal("10000")));
        baseUserLogList.add(new SendUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 10:55:00"), customerId, accountNum, new BigDecimal("910000"), receivingBank, receivingAccountName, new BigDecimal("100000")));
        baseUserLogList.add(new WithdrawUserLog(DtfZonedDateTimeUtil.convertZonedDateTime("2017-07-05 11:00:00"), customerId, accountNum, new BigDecimal("800000")));
    }

    /**
     * 룰 1: 계좌 개설 1000일 이내
     * 룰 2: 90만원~100만원 입금 후에 2시간 이내 잔고 2만원
     */
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

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate(baseUserLogList);

        if(ZonedDateTimeUtil.dayDiff(openAccountDate, ZonedDateTime.now(ZoneId.of("Asia/Seoul"))) > withinDays)
            Assert.assertFalse(result);
        else
            Assert.assertTrue(result);

    }


    /**
     * 룰 1: 계좌 개설 1일 이내
     * 룰 2: 80만원~100만원 입금 후에 2시간 이내 잔고 2만원
     */
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

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate(baseUserLogList);

        Assert.assertFalse(result);

    }

    /**
     * 룰 1: 계좌 개설 3650일 이내
     * 룰 2: 200만원~1000만원 입금 후에 2시간 이내 잔고 2만원
     */
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

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate(baseUserLogList);

        Assert.assertFalse(result);

    }

    /**
     * 룰 1: 계좌 개설 3650일 이내
     * 룰 2: 200만원~1000만원 입금 후에 2시간 이내 잔고 2만원
     *
     * 단 사용자 로그가 null
     */
    @Test
    public void testEvaluate4() {

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

        /**
         * check rule
         *
         * : User log list is null
         */
        boolean result = compositeRule.evaluate(null);

        Assert.assertFalse(result);

    }

    /**
     * Rule 이 없는 경우 : ERROR
     */
    @Test
    public void testEvaluate5() {
        /**
         * Final step : composite Rule Generation
         */
        CompositeRule compositeRule = new CompositeRule();

        /**
         * check rule
         */
        boolean result = compositeRule.evaluate(baseUserLogList);

        Assert.assertFalse(result);

    }

}
