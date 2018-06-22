package test.frame.language;

import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.rpc.PlayRpc;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jd.jr.pmock.agent.Pmock.mock;
import static com.jd.jr.pmock.agent.Pmock.mockObject;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-19
 * Time: 下午12:05
 */
public class CaseScriptMockTest {

    private final static Logger logger = LoggerFactory.getLogger(CaseScriptMockTest.class);
    //无侵入式
    @Test
    public void groovyAgentTest(){
//-javaagent:D:\github\pmock\pmock-agent\target\pmock-agent.jar
        PersonBusinessDao personBusinessDao =
              new PersonBusinessDaoImpl();
        int result = personBusinessDao.queryCount(1);
        logger.info("agent mock结果="+result+"");
    }
    @Test
    public void groovyMockTest(){
        PersonBusinessDao personBusinessDao =
                mock(PersonBusinessDaoImpl.class);
        int result = personBusinessDao.queryCount(2);
        logger.info("groovy mock结果="+result+"");
    }
    @Test
    public void groovyTest(){
        PersonBusinessDao personBusinessDao =
                mock(PersonBusinessDao.class);
       int result = personBusinessDao.queryCount(1);
        logger.info("groovy mock结果="+result+"");
    }
    @Test
    public void rubyTest(){
        RubyTest rubyTest = mock(RubyTest.class);
        String result =   rubyTest.test("a","b");
        logger.info("ruby mock结果="+result+"");
    }
    @Test
    public void pythonTest(){
        PythonTest pythonTest = mock(PythonTest.class);
        String result =   pythonTest.printme("b");
        logger.info("python mock结果="+result+"");

        }
    @Test
    public void jsTest(){
        JsTest jsTest = mock(JsTest.class);
        String result =   jsTest.execute("1", "2");
        logger.info("js mock结果="+result+"");
    }

    //无侵入式
    @Test
    public void groovyAgentTest1(){
//-javaagent:D:\github\pmock\pmock-agent\target\pmock-agent.jar
        PersonBusinessDao personBusinessDao =
                new PersonBusinessDaoImpl();
        Long result = personBusinessDao.queryLong(3L);
        logger.info("结果="+result+"");


        PersonBusinessDao personBusinessDao2 =
                mock(PersonBusinessDao.class);
        result = personBusinessDao2.queryLong(3L);
        logger.info("结果="+result+"");

        PersonBusinessDao personBusinessDao1 =
                mock(PersonBusinessDaoImpl.class);
        result = personBusinessDao1.queryLong(3L);
        logger.info("结果="+result+"");
    }
}
