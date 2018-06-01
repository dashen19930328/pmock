package com.jd.jr.pmock.demo;

import com.jd.jr.pmock.agent.Pmock;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.List;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-30
 * Time: 下午5:09
 */
public class PersonBusinessServiceTest {


       PersonBusinessService personBusinessService;
        @Test
        public void querySiddleSchoolStudentsTest(){
            if(personBusinessService==null){
                personBusinessService = new PersonBusinessServiceImpl();
                PersonBusinessDao personBusinessDao = Pmock.mock(PersonBusinessDao.class);
                Pmock.mockField(personBusinessDao,"personBusinessDao", personBusinessService);
            }
            PersonQuery query = new PersonQuery();
            List<Student> studentList = personBusinessService.queryStudents(1);
            assertNotNull(studentList);
            //assertThat( studentList.size(), is(4) );
            System.out.println("测试通过");
        }
}
