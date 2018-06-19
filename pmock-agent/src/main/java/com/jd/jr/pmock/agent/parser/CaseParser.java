package com.jd.jr.pmock.agent.parser;

import java.util.Map;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-13
 * Time: 下午4:15
 */
public interface CaseParser {
    public Map<String, String> parseCaseMethod(String caseString);
}
