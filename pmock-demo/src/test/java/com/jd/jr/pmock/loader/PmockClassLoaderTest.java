package com.jd.jr.pmock.loader;

import com.jd.jr.pmock.agent.loader.PmockClassLoader;
import com.jd.jr.pmock.demo.vo.PersonVo;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午9:18
 */
public class PmockClassLoaderTest {

    @Test
    public void classloaderTest() throws Exception {
        PmockClassLoader pmockClassLoader = new PmockClassLoader();
        PersonVo personVo = (PersonVo) pmockClassLoader.loadClass(PersonVo.class.getName()).newInstance();
        System.out.println("不对类的方法织入代码,但使用自定义类加载器加载，加载器名字:" + personVo.getClass().getClassLoader().getClass().getName());

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(PersonVo.class.getName());
        CtMethod cm = cc.getDeclaredMethod("getName");
        cm.insertBefore("System.out.println(\"调用前\");");//调用前
        cm.insertAfter("System.out.println(\"调用后\");");//调用后
        try {
            Class clazz = cc.toClass();
            PersonVo obj = (PersonVo) clazz.newInstance();
            System.out.println(obj.getName());
        } catch (Exception e) {
            System.out.println("使用默认加载器重新装载织入了代码的类，异常:" + e.getMessage());
        }
        Object newObject = pmockClassLoader.loadClass(PersonVo.class.getName(), cc.toBytecode()).newInstance();//由于是新classloader加载的实例，会cast异常
        //java.lang.ClassCastException: com.jd.jr.pmock.demo.vo.PersonVo cannot be cast to com.jd.jr.pmock.demo.vo.PersonVo
        System.out.println("对类的方法织入代码,且使用自定义类加载器加载，加载器名字:" + newObject.getClass().getClassLoader().getClass().getName());
        PersonVo obj1 = (PersonVo) newObject;
    }

    @Test
    public void classloaderNewTest() throws Exception {
        //PersonVo.class.getName();
        ClassLoadTest classLoadTest = new ClassLoadTest();
        PersonVo obj1 = (PersonVo) classLoadTest.create(PersonVo.class);
        System.out.println(obj1.getName());

    }
}
