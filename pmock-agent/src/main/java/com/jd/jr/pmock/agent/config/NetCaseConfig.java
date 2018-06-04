package com.jd.jr.pmock.agent.config;

import java.util.Map;

/**
 * 网络远程加载mock条件
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午4:56
 */
public class NetCaseConfig implements CaseConfig {
    @Override
    public void loadCase() {

    }

    @Override
    public String getCase(String caseClassName, String caseMethodName) {
        return null;
    }

    @Override
    public Map<String, String> getClassConfig(String caseClassName) {
        return null;
    }
}
