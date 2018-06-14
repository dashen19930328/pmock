package com.jd.jr.pmock.agent;

import com.jd.jr.pmock.agent.javassist.JavassistHelper;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午9:44
 */
public class JavassistHelperTest {
    @Test
    public void getNewOjectTest() throws Exception{
//javassist.CannotCompileException: by java.lang.LinkageError: loader (instance of  sun/misc/Launcher$AppClassLoader): attempted  duplicate class definition for name: "com/jd/jr/pmock/demo/dao/PersonBusinessDaoImpl"
       // PersonBusinessDaoImpl  personBusinessDao  =   JavassistHelper.getNewOject(PersonBusinessDaoImpl.class);

     PersonBusinessDaoImpl  personBusinessDao  =   JavassistHelper.getNewOject("com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl");

        PersonBusinessDaoImpl  personBusinessDao1 = new PersonBusinessDaoImpl();
        System.out.println(personBusinessDao);
        System.out.println(personBusinessDao1);
    }

}
