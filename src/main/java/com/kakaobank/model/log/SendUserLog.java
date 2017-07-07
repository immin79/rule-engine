package com.kakaobank.model.log;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */

import java.math.BigDecimal;
import java.util.Date;

/**
 * 이체 이벤트
  - 발생시각
  - 고객번호
  - 송금 계좌번호

  - 송금 이체전 계좌잔액
  - 수취 은행
  - 수취 계좌주
  - 이체 금액
 */
public class SendUserLog extends BaseUserLog {

    /**
     * 송금 이체전 계좌잔액
     */
    private BigDecimal balanceBeforeSend;

    public BigDecimal getBalanceBeforeSend() {
        return this.balanceBeforeSend;
    }

    /**
     * 수취 은행
     */
    private long receivingBank;

    public long getReceivingBank() {
        return receivingBank;
    }

    /**
     * 수취 계좌주
     */
    private String receivingAccountName;

    public String getReceivingAccountName() {
        return receivingAccountName;
    }

    /**
     * 이체 금액
     */
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return this.amount;
    }


    public SendUserLog(Date logDate, long customerId, String accountNum, BigDecimal balanceBeforeSend, long receivingBank, String receivingAccountName, BigDecimal amount) {
        super(logDate, customerId, accountNum);

        this.balanceBeforeSend = balanceBeforeSend;
        this.receivingBank = receivingBank;
        this.receivingAccountName = receivingAccountName;
        this.amount = amount;
    }
}
