package groovy.spring;

import com.jd.jr.pmock.server.dao.CaseConfigMapper;
import com.jd.jr.pmock.server.domain.CaseConfigVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-22
 * Time: 下午4:59
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class SqlDatasourceTest {

    @Autowired
    CaseConfigMapper caseConfigMapper;

    @Test
    public void test(){
        List<CaseConfigVo> caseConfigVoList = caseConfigMapper.queryBySelective(null);
        System.out.println("-----------------"+caseConfigVoList.size());
       // SpringGroovy springGroovy = new SpringGroovy();
        //springGroovy.getBean();
   WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        //  CaseConfigMapper caseConfigMapper = (CaseConfigMapper) applicationContext.getBean("caseConfigMapper");
    }
}
