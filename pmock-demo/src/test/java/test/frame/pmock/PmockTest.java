package test.frame.pmock;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.rpc.PlayRpc;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Student;
import javassist.test.entity.Employer;
import javassist.test.entity.EmployerUpdate;
import javassist.test.entity.EmployerUpdateImpl;
import org.junit.Test;


import java.util.List;

import static com.jd.jr.pmock.agent.Pmock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 上午11:10
 */
public class PmockTest {
    /**
     * 直接mock出对象
     * @throws Exception
     */
    @Test
    public void mockTest() throws Exception {
        EmployerUpdate proxyOject = mock(EmployerUpdateImpl.class);
        Employer employer = new Employer();
        employer.setName("test");
        Employer employerResult = proxyOject.query(employer);
        System.out.println(employerResult.getName());
    }

    /**
     * 正常风格的mock
     */
    @Test
    public void commonMock(){
        PersonBusinessService personBusinessService = new PersonBusinessServiceImpl();
        List<PersonVo> personVoList  =mockField(mock(PersonBusinessDao.class),"personBusinessDao",personBusinessService)
                .queryPersonList(null);
        System.out.println(personVoList.get(0).getName());
    }

    @Test
    public void commonMock1(){
        PersonBusinessServiceImpl personBusinessService = new PersonBusinessServiceImpl();
        PersonBusinessDao personBusinessDao = mock(PersonBusinessDao.class);
        personBusinessService.setPersonBusinessDao(personBusinessDao);
        System.out.println(personBusinessService.queryStudents(1).get(0).getName());
    }

    /**
     * 函数式编程风格的mock测试
     */
    @Test
    public void functionStyleMock0(){
        PersonBusinessService personBusinessService = new PersonBusinessServiceImpl();
        // mockObject(PersonBusinessDao.class).mockField("queryAge");//报错
        //mockTarget(personBusinessService).mockField("queryAge");//报错
        // mockTarget(personBusinessService).mockObject(PersonBusinessDao.class).mockField("queryAge");//报错
        mockTarget(personBusinessService).mockObject(PersonBusinessDao.class).mockField("personBusinessDao");
        List<Student> studentList = personBusinessService.queryStudents(1);
        System.out.print(studentList.get(0).getName());
    }
    /**
     * 函数式编程风格的pmock测试
     */
    @Test
    public void functionStyleMock1(){
/*        PersonBusinessService personBusinessService =
                Pmock.mockTarget(PersonBusinessServiceImpl.class).
                        mockObject(PersonBusinessDao.class).
                        mockField("personBusinessDao").target();*/

        PersonBusinessService personBusinessService =
                mockObject(PersonBusinessDao.class).
                        mockTarget(PersonBusinessServiceImpl.class).
                        mockObject(PlayRpc.class).
                        mockField("playRpc").
                        mockField("personBusinessDao").target();
        List<Student> studentList = personBusinessService.queryStudents(1);
        System.out.println(studentList.get(0).getName());
        assertNotNull(studentList);
        assertThat(studentList.size(),is(5));
        PersonVo personVo = personBusinessService.queryComplex(null);
        System.out.println(JSON.toJSON(personVo));
        System.out.println(   personBusinessService.queryString());
        System.out.println(personBusinessService.queryCount());
        System.out.println(personBusinessService.queryCountInter());

    }
    @Test
    public void functionStyleMockSimple(){
        PersonBusinessService personBusinessService = new PersonBusinessServiceImpl();
        List<Student> studentList =((PersonBusinessService)mockTarget(personBusinessService).
                mockObject(PersonBusinessDao.class).
                mockField("personBusinessDao").target()).queryStudents(1);
        System.out.println(studentList.get(0).getName());
        assertNotNull(studentList);
        assertThat(studentList.size(),is(5));
        PersonVo personVo = personBusinessService.queryComplex(null);
        System.out.println(JSON.toJSON(personVo));
        System.out.println(   personBusinessService.queryString());
        System.out.println(personBusinessService.queryCount());
        System.out.println(personBusinessService.queryCountInter());

    }



    /**
     * 通过agent 无侵入mock
     * javaagent:...\pmock-agent.jar
     */
    @Test
    public void agentNoFeelMock(){
        PersonBusinessDao personBusinessDao = new PersonBusinessDaoImpl();
        PersonBusinessServiceImpl personBusinessService =new PersonBusinessServiceImpl();
        personBusinessService.setPersonBusinessDao(personBusinessDao);
        List<Student> studentList = personBusinessService.queryStudents(1);
        System.out.println(studentList.get(0).getName());
        assertNotNull(studentList);
        assertThat(studentList.size(),is(5));
        PersonVo personVo = personBusinessService.queryComplex(null);
        System.out.println(JSON.toJSON(personVo));
        System.out.println( "测试返回类型是字符串的:"+  personBusinessService.queryString());
        System.out.println("测试返回类型是int的:"+personBusinessService.queryCount());
        System.out.println("测试返回类型是Integer的:"+personBusinessService.queryCountInter());
    }



}
