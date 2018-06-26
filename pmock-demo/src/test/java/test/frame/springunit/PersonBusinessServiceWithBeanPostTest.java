package test.frame.springunit;

import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * User: yangkuan@jd.com
 * Date: 18-3-21
 * Time: 下午2:14
 */

@Test
@ContextConfiguration(locations = {"classpath:spring/spring-config.xml"})
public class PersonBusinessServiceWithBeanPostTest extends AbstractTestNGSpringContextTests  {
    //当启动spring容器时，PmockBeanPostProcesser会进行bean过滤，发现需要mock的bean，返回mock代理bean
    @Autowired
    PersonBusinessService personBusinessService;
    @Test()
    public void testQueryStudents() throws Exception {
        List<Student> studentList = personBusinessService.queryStudents(1);
        assertNotNull(studentList);
        assertThat(studentList.size(),is(5));
        System.out.println("测试结束,从人员列表中查询到的学生数量"+studentList.size());
    }

    @Test
    public void testQueryAge() throws Exception {
        PersonQuery personQuery = new PersonQuery();
        personQuery.setAge(1);
        personQuery.setName("queryAge");
        PersonVo personVo = personBusinessService.queryAge(personQuery);
        assertNotNull(personVo);
        assertThat(personVo.getAge(),is(1));
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

    private void setField(Object mockObject,Object targetObject,String fieldName ) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject,mockObject);
    }


}
