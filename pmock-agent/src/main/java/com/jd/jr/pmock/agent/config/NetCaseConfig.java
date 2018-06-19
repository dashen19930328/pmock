package com.jd.jr.pmock.agent.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.jr.pmock.agent.http.PoolingHttpClient;
import com.jd.jr.pmock.agent.util.InitConfigUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络远程加载mock条件
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午4:56
 */
public class NetCaseConfig implements CaseConfig {
    private final static Logger log = LoggerFactory.getLogger(NetCaseConfig.class);
    @Override
    public void loadCase() {
       String mockserverUrl =  InitConfigUtil.get("mockserver");
        try {
            HttpClient httpClient = new PoolingHttpClient().create();
            HttpUriRequest httpUriRequest =new HttpGet(mockserverUrl);
            HttpResponse response = httpClient.execute(httpUriRequest);
            HttpEntity resEntity = response.getEntity();
            InputStream is = resEntity.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                baos.write(ch);
            }
            byte bytes[] = baos.toByteArray();
            String responseContent = new String(bytes, "UTF-8");
            List<Object> caseList = JSON.parseArray(responseContent);
            for(Object caseObject:caseList){
                JSONObject jsonObject = (JSONObject) caseObject;
                Map<String, String> scriptMap = new HashMap<String,String>();
                scriptMap.put("scriptText",jsonObject.getString("caseText"));
                scriptMap.put("scriptType",jsonObject.getString("scriptType"));
                caseNameMap.put(jsonObject.getString("className"), scriptMap);
            }
        } catch (Exception e) {
            String info = "网络加载case配置异常:"+e.getMessage();
            log.info(info);
        }
    }


    @Override
    public String getCaseByMethod(String caseClassName, String caseMethodName) {
        if (caseNameMap == null || caseNameMap.size() == 0)
            loadCase();
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
