package com.immin79.engine.impl;


import com.immin79.engine.Rule;
import com.immin79.model.log.BaseUserLog;
import com.immin79.model.log.OpenAccountUserLog;
import com.immin79.util.ZonedDateTimeUtil;
import org.apache.log4j.Logger;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 *
 * 계좌개설 조건 : 몇일 이내
 */
public class OpenAccountRule extends Rule {

    final static Logger logger = Logger.getLogger(OpenAccountRule.class);

    private int withinDays;

    public OpenAccountRule(int withinDays) {
        this.withinDays = withinDays;
    }

    public String getName() {

        return "Open account Rule";
    }

    public String getDescription() {

        return getName() + " : within " + withinDays + " days";
    }

    public boolean evaluate(List<BaseUserLog> baseUserLogList) {

        logger.debug(this.getName() + " evaluate is called.");
        logger.debug(" - " + this.getDescription());

        if(baseUserLogList == null) {
            logger.warn("There is no user logs!");
            return false;
        }

        // 정렬 데이터가 아닐 경우, 발생 시간 순으로 오름차순 정렬
        // TODO 정렬 데이터일 경우, 주석 처리 할 것!
        Collections.sort(baseUserLogList);

        OpenAccountUserLog openAccountUserLog = null;

        for(BaseUserLog baseUserLog : baseUserLogList) {

            if(baseUserLog instanceof OpenAccountUserLog) {
                openAccountUserLog = (OpenAccountUserLog) baseUserLog;
                break;
            }
        }

        // 계좌 개설이 withinDays 이내일 경우 (기준 : 현재 시점과의 차이)
        if(openAccountUserLog != null
                && ZonedDateTimeUtil.dayDiff(ZonedDateTime.now(ZoneId.of("Asia/Seoul")), openAccountUserLog.getLogDate()) <= withinDays) {
                return true;
        }

        return false;
    }
}
