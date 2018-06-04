package test.frame.springunit;

import com.jd.jr.pmock.agent.util.InitConfigUtil;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.rpc.PlayRpc;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.jd.jr.pmock.agent.Pmock.mockObject;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * User: yangkuan@jd.com
 * Date: 18-3-21
 * Time: 下午2:14
 */

public class SpringFlexibleTest  {
    PersonBusinessService personBusinessService;
    @BeforeTest
    public void setUp() throws Exception {
       String testType = InitConfigUtil.getVal("testType","test.properties");
        if(testType.equals("spring")){
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    new String[]{"spring/spring-config.xml"
                    });
            personBusinessService =  (PersonBusinessService) applicationContext.getBean("personBusinessService");
        }else{
             personBusinessService =
                    mockObject(PersonBusinessDao.class).
                            mockTarget(PersonBusinessServiceImpl.class).
                            mockObject(PlayRpc.class).
                            mockField("playRpc").
                            mockField("personBusinessDao").target();
        }
    }

    @Test()
    public void testQueryStudents() throws Exception {
        List<Student> studentList = personBusinessService.queryStudents(1);
        assertNotNull(studentList);
        assertThat(studentList.size(),is(5));
        System.out.println("测试结束,从人员列表中查询到的学生数量"+studentList.size());
    }

}
