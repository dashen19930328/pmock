package com.jd.jr.pmock.server.dao;


import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.server.domain.CaseConfigVo;
import com.jd.jr.pmock.server.query.PageQuery;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.hamcrest.Matchers;

import java.util.List;

public  class CaseConfigMapperTest {
    CaseConfigMapper caseConfigMapper;
    @BeforeTest
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"spring-config.xml"
                });
        caseConfigMapper =  (CaseConfigMapper) applicationContext.getBean("caseConfigMapper");

    }
    @Test
    public void saveTest(){
        CaseConfigVo caseConfigVo = new CaseConfigVo();
        caseConfigVo.setClassName("CaseConfigVo");
        caseConfigVo.setCaseText("text");
        Integer result = caseConfigMapper.save(caseConfigVo);
        Assert.assertThat(result, Matchers.equalTo(1));

    }

    @Test
    public void updateTest(){

          CaseConfigVo caseConfigVo = caseConfigMapper.queryByPrimaryKey(1l);
        Assert.assertNotNull(caseConfigVo);
        caseConfigVo.setCaseText("11111");
        Integer result = caseConfigMapper.updateByPrimaryKey(caseConfigVo);
        Assert.assertThat(result, Matchers.equalTo(1));

    }


    @Test
    public void queryTest(){
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNo(0);
        pageQuery.setPageSize(10);
        List<CaseConfigVo> caseConfigVoList = caseConfigMapper.queryBySelectiveForPagination(pageQuery);
        Assert.assertNotNull(caseConfigVoList);
        System.out.println("-------"+JSON.toJSONString(caseConfigVoList));

    }
}
