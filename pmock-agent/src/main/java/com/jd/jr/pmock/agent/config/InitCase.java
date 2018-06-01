package com.jd.jr.pmock.agent.config;

import com.jd.jr.pmock.agent.util.InitConfigUtil;

/**
 * mock脚本条件初始化入口
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午4:39
 */
public class InitCase {
    private static String caseLoadVar = "loadSource";
    private static CaseConfig caseConfig = null;

    public static String getCase(String caseClassName, String caseMethodName) {
        try {
            getCaseConfig();
            if (caseConfig != null)
                return caseConfig.getCase(caseClassName, caseMethodName);
        } catch (Exception e) {
            System.out.println("InitCase类，加载case到内存失败:" + e.getMessage());
        }
        return null;
    }

    public static Boolean classIsConfig(String caseClassName) {
        try {
            getCaseConfig();
            if (caseConfig != null && caseConfig.getClassConfig(caseClassName) != null)
                return Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("InitCase类，加载case到内存失败:" + e.getMessage());
        }
        return Boolean.FALSE;
    }

    private static void getCaseConfig() {
        if (caseConfig != null) return;
        String loadSource = InitConfigUtil.get(caseLoadVar);
        if (loadSource != null) {
            if (loadSource.equals("local")) {
                caseConfig = new LocalCaseConfig();
            }
            if (loadSource.equals("net")) {
                caseConfig = new NetCaseConfig();
            }
        }
    }
}
