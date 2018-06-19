package com.jd.jr.pmock.agent.config;

import com.jd.jr.pmock.agent.parser.ParserRouter;
import com.jd.jr.pmock.agent.util.CaseFileUtil;
import com.jd.jr.pmock.agent.parser.GroovyCaseParser;

import java.io.File;
import java.util.HashMap;
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
                String[] caseClassName = caseFileName.split("\\.");
                String className = caseClassName[0];
                String prefix=caseFileName.substring(caseFileName.lastIndexOf(".")+1);
                String scriptText = CaseFileUtil.readToString(caseFile);
                Map<String, String> scriptMap = new HashMap<String,String>();
                scriptMap.put("scriptText",scriptText);
                scriptMap.put("scriptType",prefix);
                if (prefix.equals("groovy")||prefix.equals("javascript")||prefix.equals("js")||prefix.equals("python")||prefix.equals("ruby")) {
                    caseNameMap.put(className, scriptMap);
                    if(ParserRouter.getParser(prefix)!=null){
                        Map<String, String> caseMethodMap = ParserRouter.getParser(prefix).parseCaseMethod(CaseFileUtil.readToString(caseFile));
                        methodMap.put(className, caseMethodMap);
                    }
                }
            }
        }
    }


    public String getCaseByMethod(String caseClassName, String caseMethodName) {
        if (caseNameMap == null || caseNameMap.size() == 0)
            loadCase();
        if (caseNameMap.get(caseClassName) != null &&
                methodMap.get(caseClassName).get(caseMethodName) != null) {
          return   "";
        }
        if (caseNameMap.get(caseClassName)!=null) {
            String caseText = caseNameMap.get(caseClassName).get("scriptText");
            if(caseText.contains(caseMethodName)){
                return "";
            }
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
