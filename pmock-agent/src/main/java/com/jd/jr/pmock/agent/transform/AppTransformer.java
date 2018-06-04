package com.jd.jr.pmock.agent.transform;


import com.jd.jr.pmock.agent.javassist.JavassistHelper;
import javassist.CtClass;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


/**
 * User: yangkuan@jd.com
 * Date: 18-5-23
 * Time: 下午8:30
 */
public class AppTransformer implements ClassFileTransformer {
    /**
     * 字节码加载到虚拟机前会进入这个方法
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            //javassist的包名是用点分割的，需要转换下
            if (className != null && className.indexOf("/") != -1) {
                className = className.replaceAll("/", ".");
            }
            CtClass ctClass = JavassistHelper.implantateMethod(className);
            if (ctClass != null) {
              return   ctClass.toBytecode();
            }
        } catch (Exception e) {
            System.out.println("javassist操作异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}