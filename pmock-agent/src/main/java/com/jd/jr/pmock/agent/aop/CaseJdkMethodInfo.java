package com.jd.jr.pmock.agent.aop;

import java.lang.reflect.Type;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午6:12
 */
public class CaseJdkMethodInfo {
    public Class<?> returnClass;
    public String returnClassName;
    public Boolean isString = Boolean.FALSE;
    public Type type;
    public Boolean returnTypeIsGeneric = Boolean.FALSE;
    public String caseSctript;
}
