package com.jd.jr.pmock.loader;

import com.jd.jr.pmock.agent.loader.PmockClassLoader;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午9:59
 */
public class ClassLoadTest {
    public <T> T  create(byte[] code,T targetObject) throws Exception {
        PmockClassLoader pmockClassLoader = new PmockClassLoader();
        return  (T) pmockClassLoader.loadClass(targetObject.getClass().getName(),code).newInstance();//由于是新classloader加载的实例，会cast异常
    }

    public Object  create(Class targetObject) throws Exception {
        ClassPool pool  = ClassPool.getDefault();
        CtClass cc = pool.get(targetObject.getName());
        CtMethod cm = cc.getDeclaredMethod("getName");
        cm.insertBefore("System.out.println(\"调用前\");");//调用前
        cm.insertAfter("System.out.println(\"调用后\");");//调用后
        PmockClassLoader pmockClassLoader = new PmockClassLoader();
        return   pmockClassLoader.loadClass(targetObject.getName(),cc.toBytecode()).newInstance();//由于是新classloader加载的实例，会cast异常
    }
}
