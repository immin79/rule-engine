package com.immin79.engine;

import com.immin79.model.log.BaseUserLog;

import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public abstract class Rule {

    public abstract String getName();

    public abstract String getDescription();

    /**
     * 특정 룰을 해당되면 true, 해당되지 않으면 false
     * @return
     */
    public abstract boolean evaluate(List<BaseUserLog> baseUserLogList);
}
