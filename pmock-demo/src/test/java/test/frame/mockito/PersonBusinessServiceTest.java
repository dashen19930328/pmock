package test.frame.mockito;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Student;

import org.hamcrest.core.Is;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: yangkuan@jd.com
 * Date: 18-3-21
 * Time: 下午2:14
 */

public class PersonBusinessServiceTest {

    PersonBusinessService personBusinessService = new PersonBusinessServiceImpl();
    @BeforeTest
    public void setUp() throws Exception {

        List<PersonVo> returenList = new ArrayList<PersonVo>();
        PersonBusinessDao mockPersonBusinessDao = mock(PersonBusinessDao.class);
        when(mockPersonBusinessDao.queryPersonList(isA(PersonQuery.class))).thenReturn(returenList);//录制Mock对象预期行为
        setField(mockPersonBusinessDao,personBusinessService,"personBusinessDao");

        PersonVo personVo = new PersonVo();
        personVo.setAge(12);
        personVo.setName("mike");
        personVo.setSex(1);
        PersonVo personVo1 = new PersonVo();
        personVo1.setAge(3);
        personVo1.setName("jim");
        personVo1.setSex(1);
        returenList.add(personVo1);
        returenList.add(personVo);
        PersonVo personVo2 = new PersonVo();
        personVo2.setAge(16);
        personVo2.setName("tom");
        personVo2.setSex(1);
        returenList.add(personVo2);

    }
    @Test
    public void testQueryStudents() {
        List<Student> studentList = personBusinessService.queryStudents(1);//男性
        assertNotNull(studentList);
        assertThat(studentList.size(),is(1));
        System.out.println("测试结束,从人员列表中查询到的学生数量:"+studentList.size());
        System.out.println(JSON.toJSON(studentList));
    }



    private void setField(   Object mockObject, Object personBusinessService,String fieldName) throws Exception {
        Field field = personBusinessService.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(personBusinessService,mockObject);
    }
}
