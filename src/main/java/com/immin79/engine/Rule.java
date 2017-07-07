package com.immin79.engine;

import com.immin79.model.log.BaseUserLog;

import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public abstract class Rule {

    protected List<BaseUserLog> baseUserLogList;

    public void setBaseUserLogList(List<BaseUserLog> baseUserLogList) {

        this.baseUserLogList = baseUserLogList;
    }

    public abstract String getName();

    public abstract String getDescription();

    /**
     * 특정 룰을 해당되면 true, 해당되지 않으면 false
     * @return
     */
    public abstract boolean evaluate();
}
