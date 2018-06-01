package com.jd.jr.pmock.agent.config;

import com.jd.jr.pmock.agent.util.CaseFileUtil;

import java.io.File;
import java.util.Map;

/**
 * 本地加载mock条件
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午4:20
 */
public class LocalCaseConfig implements CaseConfig {
    private String caseDir = "pmock/caseConfig";

    public void loadCase() {
        File caseDirFile = new File(this.getClass().getResource("/" + caseDir).getPath());
        if (caseDirFile.isDirectory()) {
            File[] caseFiles = caseDirFile.listFiles();
            for (File caseFile : caseFiles) {
                String caseFileName = caseFile.getName();
                if (caseFileName.lastIndexOf(".groovy") > -1) {
                    String[] caseClassName = caseFileName.split("\\.");
                    String className = caseClassName[0];
                    Map<String, String> caseMethodMap = CaseFileUtil.readCaseText(CaseFileUtil.readToString(caseFile));
                    caseNameMap.put(className, caseMethodMap);
                }
            }
        }
    }


    public String getCase(String caseClassName, String caseMethodName) {
        if (caseNameMap == null || caseNameMap.size() == 0)
            loadCase();
        if (caseNameMap.get(caseClassName) != null &&
                caseNameMap.get(caseClassName).get(caseMethodName) != null) {
            return caseNameMap.get(caseClassName).get(caseMethodName);
        }
        return null;
    }

    @Override
    public Map<String, String> getClassConfig(String caseClassName) {
        if (caseNameMap == null || caseNameMap.size() == 0)
            loadCase();
        return caseNameMap.get(caseClassName);
    }
}
