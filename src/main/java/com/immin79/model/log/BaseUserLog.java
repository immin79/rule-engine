package com.immin79.model.log;

import java.util.Date;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */

/**
 * 기본 사용자 로그
 *
 - 발생시각
 - 고객번호
 - 계좌번호

 * c.f) input log 가 순서대로 들어오지 않을 경우 Collections.sort 로 오름차순 정렬 지원 : Comparable
 */
public class BaseUserLog implements Comparable<BaseUserLog> {

    /**
     * 발생 일시
     */
    private Date logDate;

    public Date getLogDate() {

        return this.logDate;
    }

    /**
     * 고객 번호
     */
    private long customerId;

    public long getCustomerId() {

        return this.customerId;
    }

    /**
     * 계좌 번호
     */
    private String accountNum;

    public String getAccountNum() {

        return this.accountNum;
    }

    public BaseUserLog(Date logDate, long customerId, String accountNum) {

        this.logDate = logDate;
        this.customerId = customerId;
        this.accountNum = accountNum;
    }

    public int compareTo(BaseUserLog o) {

        if (getLogDate() == null || o.getLogDate() == null)
            return 0;
        return getLogDate().compareTo(o.getLogDate());
    }
}
