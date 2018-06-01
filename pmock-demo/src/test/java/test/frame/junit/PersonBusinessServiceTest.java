package test.frame.junit;

import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Student;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User: yangkuan@jd.com
 * Date: 18-3-21
 * Time: 下午2:14
 */
public class PersonBusinessServiceTest {

    //人员查询service
    PersonBusinessServiceImpl  personBusinessService = new PersonBusinessServiceImpl();

    @BeforeTest
    public void setUp() throws Exception {
        PersonBusinessDao personBusinessDao = new PersonBusinessDaoImpl();
        //设置service的属性。dao可能从数据库查询或者第三方系统查询
        personBusinessService.setPersonBusinessDao(personBusinessDao);
    }
    @Test
    public void testQueryStudents(){
        //从男性人员列表中，筛选出学生列表
        List<Student> studentList = personBusinessService.queryStudents(1);//男性
        assertNotNull(studentList);
        assertThat(studentList.size(),is(0));
        System.out.println("测试结束,从人员列表中查询到的学生数量"+studentList.size());
    }
    @Test
    public void testQueryAge() throws Exception {
        PersonQuery personQuery = new PersonQuery();
        personQuery.setAge(1);
        personQuery.setName("queryAge");
        PersonVo personVo = personBusinessService.queryAge(personQuery);
        assertNotNull(personVo);
        //assertThat(personVo.getAge(),is(1));
        System.out.println("测试通过");
    }

    @Test
    public void testQueryName() throws Exception {
        PersonQuery personQuery = new PersonQuery();
        personQuery.setAge(1);
        personQuery.setName("queryName");
        PersonVo personVo = personBusinessService.queryName(personQuery);
        assertNotNull(personVo);
        assertEquals("queryName",personVo.getName());
        System.out.println("测试通过");
    }
}
