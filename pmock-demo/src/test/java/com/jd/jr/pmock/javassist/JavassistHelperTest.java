package com.jd.jr.pmock.javassist;

import com.jd.jr.pmock.agent.javassist.JavassistHelper;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午9:44
 */
public class JavassistHelperTest {
    @Test
    public void getNewOjectTest() throws Exception{
        //PersonBusinessDao personBusinessDao1 =  new PersonBusinessDaoImpl();
      //  Class classObject = PersonBusinessDaoImpl.class; //如果不使用自定义类加载器，多了这两行，将报错duplicate
        //javassist.CannotCompileException: by java.lang.LinkageError: loader (instance of  sun/misc/Launcher$AppClassLoader):
        // attempted  duplicate class definition for name: "com/jd/jr/pmock/demo/dao/PersonBusinessDaoImpl"
        PersonBusinessDao personBusinessDao  =   JavassistHelper.getNewOject("com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl");
        PersonQuery query = new PersonQuery();
        query.setName("test");
        System.out.println(personBusinessDao.queryPersonList(query).get(0).getName());
        System.out.println(personBusinessDao.getClass().getClassLoader().getClass().getName());
    }
    @Test
    public void getNewOjectTestFromClass() throws Exception{
        //Class personVoClass = PersonBusinessDaoImpl.class;
        String name = getClassName(PersonBusinessDaoImpl.class);
        System.gc();
        PersonBusinessDao personBusinessDao  =   JavassistHelper.getNewOject(name);
        System.out.println(personBusinessDao.queryPersonList(null));
    }
    public String getClassName(Class classObject){
        String name = classObject.getName();
        classObject = null;
        return name;
    }
}
