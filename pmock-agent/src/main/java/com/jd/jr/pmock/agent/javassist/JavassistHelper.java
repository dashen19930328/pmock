package com.jd.jr.pmock.agent.javassist;

import com.jd.jr.pmock.agent.config.InitCase;
import com.jd.jr.pmock.agent.loader.PmockClassLoader;
import javassist.*;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午8:12
 */
public class JavassistHelper {
    /**
     * 像对象的方法植入代码
     *
     * @param className
     * @return
     */
    public static CtClass implantateMethod(String className) throws NotFoundException, CannotCompileException {
        String simpleName = className.substring(className.lastIndexOf(".") + 1);
        //通过包名获取类文件
        CtClass cc = null;
        if (!InitCase.classIsConfig(simpleName))
            return null;
        CtMethod[] ctMethods = null;
        try {
            cc = ClassPool.getDefault().get(className);
            if(cc.isInterface()){//接口不能通过agent aop
                return null;
            }
            ctMethods = cc.getDeclaredMethods();
        } catch (Exception e) {
            return null;
        }
        for (CtMethod method : ctMethods) {
            if (needSisst(method, simpleName)) {
                //在方法执行后插入代码，才能使用$_  $r
                StringBuffer afterCode = new StringBuffer();
                 afterCode.append(String.format(" $_ = ($r)%s.afterInvoke0(\"%s\",$args, $0);", new Object[]{"com.jd.jr.pmock.agent.aop.AopProxyHandler", method.getName()}));

                method.insertAfter(afterCode.toString());
            }
        }
        return cc;
    }

    /**
     * 识别具体方法是否aop
     * @param method
     * @param simpleName
     * @return
     * @throws NotFoundException
     */
    private static Boolean needSisst(CtMethod method, String simpleName) throws NotFoundException {
        String methodName = method.getName();
        CtClass returnType = method.getReturnType();
        String returnName = returnType.getName();
        if (!returnName.equals("void")) {
            if (InitCase.getCase(simpleName, methodName) != null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 给代理类设置方法
     *
     * @param targetMethod
     * @param proxyObjectClass
     * @return
     * @throws NotFoundException
     */
    public static CtMethod makeProxyObjectMethod(CtMethod targetMethod, CtClass proxyObjectClass,Class targetObject) throws NotFoundException, CannotCompileException {
        String methodName = targetMethod.getName();
        CtClass returnType = targetMethod.getReturnType();

        System.out.println(targetMethod.getName() + "=" + returnType.getName());
      CtMethod proxyMethod = new CtMethod(returnType, methodName, targetMethod.getParameterTypes(), proxyObjectClass);
     //proxyMethod.setGenericSignature(new SignatureAttribute.TypeVariable("Repository<String>").encode());
      /*  if(returnType.getGenericSignature()!=null){
            proxyMethod.setGenericSignature(returnType.getGenericSignature());
        }*/
        //CtNewMethod.make("public Integer getInteger() { return null; }", cc);
        if(returnType.getGenericSignature()!=null){//搞不定泛型设置
        MethodInfo methodInfo =  targetMethod.getMethodInfo();
       //  proxyMethod =    CtMethod.make(targetMethod.getMethodInfo(),proxyObjectClass);
        //proxyMethod.setGenericSignature(new SignatureAttribute.TypeVariable("Repository<String>").encode());
        }
        String classSimpleName = targetObject.getSimpleName();
        if (returnType.getName().equals("void")) {//不需要返回对象的，简单处理
            proxyMethod.setBody("{System.out.print(\"\"); }");
        } else {
            proxyMethod.setBody("{return null; }");
            if (InitCase.getCase(classSimpleName, methodName) != null) {//设置了mock，继续处理
                StringBuffer afterCode = new StringBuffer();
                afterCode.append(String.format(" $_ = ($r)%s.afterInvoke1(\"%s\",$args, $0,\"%s\");", new Object[]{"com.jd.jr.pmock.agent.aop.AopProxyHandler", methodName, classSimpleName}));
                proxyMethod.insertAfter(afterCode.toString());
            }
        }
        return proxyMethod;
    }

    /**
     * 创建接口的代理class
     *
     * @param targetObject
     * @return
     */
    public static CtClass makeProxyObjectClass(Class targetObject) {
        ClassPool pool = ClassPool.getDefault();
        String newClassName = getPackageName(targetObject) + getProxyObjectName(targetObject);
        CtClass proxyObjectClass = pool.makeClass(newClassName); //创建代理class
        return proxyObjectClass;
    }
    /**
     * 被代理目标如果是接口，作为赋给代理class；被代理目标如果是类，将其赋给代理class的上级类，如果有接口也赋给
     *
     * @param targetObject
     * @param proxyObjectClass
     * @return
     * @throws NotFoundException
     */
    public static void wrapInterfacesAndSupper(Class targetObject, CtClass proxyObjectClass) throws NotFoundException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass superClass = pool.getCtClass(getPackageName(targetObject) + "." + targetObject.getSimpleName()); //获取代理对象的接口类
        if(targetObject.isInterface()){//如果被代理对象是接口
            CtClass[] interfaces = new CtClass[]{superClass};
            proxyObjectClass.setInterfaces(interfaces);
        }else{
            proxyObjectClass.setSuperclass(superClass);
            CtClass[] interfaces =pool.get(targetObject.getName()).getInterfaces();
            proxyObjectClass.setInterfaces(interfaces);

        }
    }
    /**
     * 被代理接口或者类的方法列表
     *
     * @param targetObject
     * @return
     * @throws NotFoundException
     */
    public static CtMethod[] getSuperMethod(Class targetObject) throws NotFoundException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass superClass = pool.getCtClass(getPackageName(targetObject) + "." + targetObject.getSimpleName()); //获取代理对象的接口类
       CtMethod[] methods = superClass.getDeclaredMethods(); //被代理接口的所有方法
        return methods;
    }


    //生成代理对象
    public static <T> T getProxyOject(Class targetObject) {
        T proxyObject = null;
        try {
            CtClass proxyObjectClass = makeProxyObjectClass(targetObject);//创建代理的class
            wrapInterfacesAndSupper(targetObject, proxyObjectClass);
            CtMethod[] superMethods = getSuperMethod(targetObject);//获得结果所有的方法
            for (CtMethod superMethod : superMethods) {
                CtMethod proxyMethod = makeProxyObjectMethod(superMethod, proxyObjectClass,targetObject);
                proxyObjectClass.addMethod(proxyMethod);//给代理类装载新方法
            }
            Class aClass = proxyObjectClass.toClass();
            proxyObject = (T) aClass.newInstance();
        } catch (Exception e) {
            System.out.println("Pmock创造代理对象异常:" + e.getMessage());
            e.printStackTrace();
        }
        return proxyObject;
    }

    public static <T> T getNewOject(Class classObject) {
       return (T)getNewOject(classObject.getName());
    }
    public static <T> T getNewOject(String objectClassFullName) {
        T newObject = null;
        try {
            CtClass ctClass = JavassistHelper.implantateMethod(objectClassFullName);
            PmockClassLoader pmockClassLoader = new PmockClassLoader();
            //担心不同classloader加载的，到前台转型异常。
            newObject = (T)  pmockClassLoader.loadClass(objectClassFullName,ctClass.toBytecode()).newInstance();
          //  newObject = (T) ctClass.toClass().newInstance();//直接使用这个new对象(如果通过Class classObject调用机进来的)，报错在同一个加载器下重复装载class（ctClass.toClass）
            //javassist.CannotCompileException: by java.lang.LinkageError: loader (instance of  sun/misc/Launcher$AppClassLoader): attempted  duplicate class definition for name: "com/jd/jr/pmock/demo/dao/PersonBusinessDaoImpl"

        } catch (Exception e) {
            System.out.println("Pmock创造新对象异常:" + e.getMessage());
            e.printStackTrace();
        }
        return  newObject;
    }

    private static final String PROXYFREFIX = "$Proxy";//生成的代理对象名称前缀
    private static final String PROXYSUFFIX = "Impl";//生成的代理对象名称前缀

    //获取包名
    public static String getPackageName(Class t) {
        Package aPackage = t.getPackage();
        String packageName = aPackage.getName();
        return packageName;
    }

    //获取代理对象的名称
    public static String getProxyObjectName(Class t) {
        return PROXYFREFIX + t.getSimpleName() + PROXYSUFFIX;
    }

}
