package com.immin79.model.log;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * 입금 이벤트
  - 발생시각
  - 고객번호
  - 계좌번호

  - 입금 금액
 */
public class DepositUserLog extends BaseUserLog {

    /**
     * 입금 금액
     */
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return this.amount;
    }

    public DepositUserLog(ZonedDateTime logDate, long customerId, String accountNum, BigDecimal amount) {
        super(logDate, customerId, accountNum);
        this.amount = amount;
    }


}
