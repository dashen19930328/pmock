package com.jd.jr.pmock.server.controller;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.server.dao.CaseConfigMapper;
import com.jd.jr.pmock.server.domain.CaseConfigVo;
import com.jd.jr.pmock.server.util.R;
import org.testng.annotations.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.AssertThrows;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeTest;

import java.util.List;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-13
 * Time: 下午5:18
 */
public class CaseConfigControllerTest {
    @Test
    public void runCaseMethodTest(){
        String text =
                "def queryCountInter(count,name){\n" +
                        "if(count=='1'){\n" +
                        "println 'haha'\n" +
                        "};"+
                "    println count;\n" +
                "    println name;\n" +
                "    return \"{'name':'author'}\";\n" +
                "}";
        CaseConfigController caseConfigController = new CaseConfigController();
        CaseConfigVo caseConfigVo = new CaseConfigVo();
        caseConfigVo.setCaseText(text);
        caseConfigVo.setScriptType("groovy");
        caseConfigVo.setMethod("queryCountInter");
        String inputPara = "1,test";
        caseConfigVo.setInputPara(inputPara);
        R result = caseConfigController.runCaseMethod(caseConfigVo);
        System.out.println("测试groovy脚本:"+JSON.toJSONString(result));


        text ="function execute(s1, s2){\n" +
                "  return s1 + s2;\n" +
                "}";
        caseConfigVo.setCaseText(text);
        caseConfigVo.setScriptType("js");
        caseConfigVo.setMethod("execute");
          inputPara = "1,test";
        caseConfigVo.setInputPara(inputPara);
         result = caseConfigController.runCaseMethod(caseConfigVo);
        System.out.println("测试js脚本:"+JSON.toJSONString(result));
    }

    @Test
    public void getCaseMethod(){

        CaseConfigController caseConfigController = new CaseConfigController();
        CaseConfigVo caseConfigVo = new CaseConfigVo();
        String text =
                "def queryCountInter(count,name){\n" +
                        "    println count;\n" +
                        "    println name;\n" +
                        "    return \"{'name':'author'}\";\n" +
                        "}";

        caseConfigVo.setCaseText(text);
        caseConfigVo.setScriptType("groovy");
        R result = caseConfigController.getCaseMethod(caseConfigVo);
        System.out.println("测试groovy脚本:"+JSON.toJSONString(result));

        text ="function execute(s1, s2){\n" +
                "  return s1 + s2;\n" +
                "};\n"+
                "function execute2(s1, s2){\n" +
                "  return s1 + s2;\n" +
                "}";
        caseConfigVo.setCaseText(text);
        caseConfigVo.setScriptType("javascript");
        result = caseConfigController.getCaseMethod(caseConfigVo);
        System.out.println("测试js脚本:"+JSON.toJSONString(result));

    }
    CaseConfigMapper caseConfigMapper;
    @BeforeTest
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"spring-config.xml"
                });
        caseConfigMapper =  (CaseConfigMapper) applicationContext.getBean("caseConfigMapper");

    }

    @Test
    public void getCaseList(){

        CaseConfigController caseConfigController = new CaseConfigController();
        caseConfigController.setCaseConfigMapper(caseConfigMapper);
        List<CaseConfigVo>  caseConfigVoList = caseConfigController.caseList("baina");
        Assert.notNull(caseConfigVoList);

        System.out.println("case列表:"+JSON.toJSONString(caseConfigVoList));
    }
}
