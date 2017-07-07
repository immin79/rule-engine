package com.kakaobank.engine;

import com.kakaobank.model.log.BaseUserLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan.79 on 2017. 7. 7..
 */
public class CompositeRule extends Rule {

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
    public boolean evaluate() {

        // 특정 룰이 없으면 사용자 Detection 하지 않음
        if(ruleList == null)
            return false;

        for(Rule rule : ruleList) {

            if(rule.evaluate() == false)
                return false;
        }

        // 모든 룰에 해당할 경우에만 true 반환
        return true;
    }

    public void setBaseUserLogList(List<BaseUserLog> baseUserLogList) {

        if(ruleList == null)
            return;

        for(Rule rule : ruleList) {
            rule.setBaseUserLogList(baseUserLogList);
        }
    }
}
