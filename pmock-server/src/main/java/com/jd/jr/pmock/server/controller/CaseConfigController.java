package com.jd.jr.pmock.server.controller;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.agent.jvmScript.ScriptRun;
import com.jd.jr.pmock.agent.parser.GroovyCaseParser;
import com.jd.jr.pmock.agent.parser.JsCaseParser;
import com.jd.jr.pmock.server.dao.CaseConfigMapper;
import com.jd.jr.pmock.server.domain.CaseConfigRunVo;
import com.jd.jr.pmock.server.domain.CaseConfigVo;
import com.jd.jr.pmock.server.query.PageQuery;
import com.jd.jr.pmock.server.query.PageRes;
import com.jd.jr.pmock.server.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-11
 * Time: 下午3:39
 */
@Controller
@RequestMapping("/caseConfig")
public class CaseConfigController {
    @Autowired
    private CaseConfigMapper caseConfigMapper;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public R save(CaseConfigVo caseConfigVo) {
        caseConfigMapper.save(caseConfigVo);
        return R.ok();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R update(CaseConfigVo caseConfigVo) {
        caseConfigMapper.updateByPrimaryKey(caseConfigVo);
        return R.ok();
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(PageQuery<CaseConfigVo> pageQuery,CaseConfigVo caseConfigVo) {
        pageQuery.setQuery(caseConfigVo);
        pageQuery.setStartRow((pageQuery.getPageNo()-1)*pageQuery.getPageSize());
        List<CaseConfigVo> caseConfigVoList = caseConfigMapper.queryBySelectiveForPagination(pageQuery);

        Long count = caseConfigMapper.queryCountBySelective(pageQuery.getQuery());

        PageRes pageRes = new PageRes(caseConfigVoList, count==null?0:count.intValue(), pageQuery);

        return R.ok().put("page", pageRes);
    }

    @ResponseBody
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CaseConfigVo caseConfigVo = caseConfigMapper.queryByPrimaryKey(id);
        return R.ok().put("caseConfig", caseConfigVo);
    }

    @RequestMapping(value = "/getCaseMethod", method = RequestMethod.POST)
    @ResponseBody
    public R getCaseMethod(CaseConfigVo caseConfigVo) {
        String scriptType = caseConfigVo.getScriptType();
        if(scriptType==null||scriptType.equals("")){
            scriptType = "groovy";
        }

        Map<String, String> methodMap = null;
        if(scriptType.equals("groovy")){
            methodMap = GroovyCaseParser.parseCaseMethod(caseConfigVo.getCaseText());
        }
        if(scriptType.equals("javascript")){
            methodMap = JsCaseParser.parseCaseMethod(caseConfigVo.getCaseText());
        }
        methodMap.remove("");
        return R.ok().put("methodMap", methodMap.keySet());
    }

    @RequestMapping(value = "/runCaseMethod", method = RequestMethod.POST)
    @ResponseBody
    public R runCaseMethod(CaseConfigVo caseConfigVo) {
        String caseText = caseConfigVo.getCaseText();
        String scriptType = caseConfigVo.getScriptType();
        if(scriptType==null||scriptType.equals("")){
            scriptType = "groovy";
        }
        String inputPara = caseConfigVo.getInputPara();
        String[] inputParaArr = null;
        Object[] inputDataObjs = null;
        if(inputPara!=null&&!inputPara.equals("")){
            inputParaArr = inputPara.split("@@@");
            inputDataObjs = new Object[inputParaArr.length];
            int index = 0;
            for(String inputData:inputParaArr){
                try{
                    inputDataObjs[index] = JSON.parse(inputData);
                }catch (Exception e){
                    inputDataObjs[index] = inputData;
                }
                index++;
            }
        }
       String methodName = caseConfigVo.getMethod();
        Object reponseObj = ScriptRun.runScript(caseText, methodName, inputDataObjs, scriptType);
        return R.ok().put("reponse", JSON.toJSONString(reponseObj));
    }
}
