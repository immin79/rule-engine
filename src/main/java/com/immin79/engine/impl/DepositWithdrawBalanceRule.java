package com.immin79.engine.impl;


import com.immin79.engine.Rule;
import com.immin79.model.log.BaseUserLog;
import com.immin79.model.log.DepositUserLog;
import com.immin79.model.log.SendUserLog;
import com.immin79.model.log.WithdrawUserLog;
import com.immin79.util.ZonedDateTimeUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 *
 * 입금 -> 출금 -> 잔액 조건
 * e.g. 90~100만원 입금 된 후에 2시간 이내 출금되어 잔액이 1만원 이하
 */
public class DepositWithdrawBalanceRule extends Rule {

    final static Logger logger = Logger.getLogger(DepositWithdrawBalanceRule.class);

    /**
     * 입금 금액 조건 1 : GTE(Greater than equal) (~원 이상)
     */
    private BigDecimal gteDepositAmount;

    /**
     * 입금 금액 조건 2 : LTE(Less than equal) (~원 이하)
     */
    private BigDecimal lteDepositAmount;

    /**
     * 입금 후 몇 시간 이내
     */
    private long withinHoursAfterDeposit;

    /**
     * 잔액 ~원 이하 LTE(Less than equal)
     */
    private BigDecimal lteBalance;

    public DepositWithdrawBalanceRule(BigDecimal gteDepositAmount, BigDecimal lteDepositAmount, long withinHoursAfterDeposit, BigDecimal lteBalance) {

        this.gteDepositAmount = gteDepositAmount;
        this.lteDepositAmount = lteDepositAmount;
        this.withinHoursAfterDeposit = withinHoursAfterDeposit;
        this.lteBalance = lteBalance;
    }

    @Override
    public String getName() {
        return "Deposit -> Withdraw within hours -> balance Rule";
    }

    @Override
    public String getDescription() {
        return getName() + " : Deposit Amount " + gteDepositAmount + " ~ " + lteDepositAmount +  " & within " + withinHoursAfterDeposit + " hours & balance limit(lte) " + lteBalance;
    }

    @Override
    public boolean evaluate(List<BaseUserLog> baseUserLogList) {

        logger.debug(this.getName() + " evaluate is called.");
        logger.debug(" - " + this.getDescription());

        if(baseUserLogList == null) {
            logger.warn("There is no user logs!");
            return false;
        }

        // TODO 정렬 데이터일 경우, 주석 처리 할 것!
        Collections.sort(baseUserLogList);

        BigDecimal balance = new BigDecimal(0);

        ZonedDateTime depositLatestDate = null;

        for(BaseUserLog baseUserLog : baseUserLogList) {

            /**
             * 계좌 잔액을 구함
             */
            // 입금 이벤트
            if(baseUserLog instanceof DepositUserLog) {
                balance = balance.add(((DepositUserLog) baseUserLog).getAmount());

                // 입금 금액 체크
                if(((DepositUserLog) baseUserLog).getAmount().compareTo(gteDepositAmount) >= 0 && lteDepositAmount.compareTo(((DepositUserLog) baseUserLog).getAmount()) >= 0) {
                    depositLatestDate = baseUserLog.getLogDate();
                }

            }
            // 출금 이벤트
            else if (baseUserLog instanceof WithdrawUserLog) {
                balance = balance.subtract(((WithdrawUserLog) baseUserLog).getAmount());
            }
            // 송금 이벤트
            else if (baseUserLog instanceof SendUserLog) {
                balance = balance.subtract(((SendUserLog) baseUserLog).getAmount());
            }

            if(depositLatestDate != null) {
                long hourDiff = ZonedDateTimeUtil.hourDiff(depositLatestDate, baseUserLog.getLogDate());

                if(hourDiff > withinHoursAfterDeposit) {

                    // 입금 후 withinHoursAfterDeposit 시간 경과 후에는 deposit 최근 시간 null 로 변경
                    depositLatestDate = null;
                } else {

                    // 입금 후 withinHoursAfterDeposit 인 경우에는 잔고가 얼마 이하 일 경우에 해당 조건에 맞음(true)를 반환
                    if(lteBalance.compareTo(balance) >= 0)
                        return true;
                }
            }
        }

        return false;
    }
}
