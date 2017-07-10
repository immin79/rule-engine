package com.immin79.engine;


import com.immin79.model.log.BaseUserLog;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class CompositeRule extends Rule {

    final static Logger logger = Logger.getLogger(CompositeRule.class);

    private List<Rule> ruleList;

    public void add(Rule rule) {
        if(ruleList == null)
            ruleList = new ArrayList<Rule>();

        ruleList.add(rule);
    }

    public void remove(Rule rule) {

        if(ruleList != null)
            ruleList.remove(rule);
    }

    @Override
    public String getName() {
        return "Composite Rule";
    }

    @Override
    public String getDescription() {
        return "Composite pattern : Rule list";
    }

    @Override
    public boolean evaluate(List<BaseUserLog> baseUserLogList) {

        logger.debug(this.getName() + " evaluate is called.");
        logger.debug(" - " + this.getDescription());

        // 특정 룰이 없으면 사용자 Detection 하지 않음
        if(ruleList == null) {
            logger.error("There is no Rule! Please add rule to CompositeRule.");
            return false;
        }

        for(Rule rule : ruleList) {

            if(rule.evaluate(baseUserLogList) == false)
                return false;
        }

        // 모든 룰에 해당할 경우에만 true 반환
        return true;
    }
}
