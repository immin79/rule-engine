package com.immin79.model.log;

import java.util.Date;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */

/**
 * 계좌 신설 이벤트
  - 발생시각
  - 고객번호
  - 계좌번호
 */
public class OpenAccountUserLog extends BaseUserLog {

    public OpenAccountUserLog(Date logDate, long customerId, String accountNum) {

        super(logDate, customerId, accountNum);
    }
}
