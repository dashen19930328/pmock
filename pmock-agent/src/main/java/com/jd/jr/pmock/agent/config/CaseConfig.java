package com.jd.jr.pmock.agent.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 装载mock条件脚本
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午4:53
 */
public interface CaseConfig {
    //key是文件名，文件名即要mock的类名。
    // value是一个map，此map的key对应要测试的方法名，value是groovy的方法内容。
    public Map<String, Map<String, String>> caseNameMap = new HashMap<String, Map<String, String>>();//

    public void loadCase();

    public String getCaseByMethod(String caseClassName, String caseMethodName);

    public Map<String, String> getClassConfig(String caseClassName);
}
