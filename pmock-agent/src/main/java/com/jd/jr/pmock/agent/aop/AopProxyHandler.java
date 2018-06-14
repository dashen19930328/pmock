package com.jd.jr.pmock.agent.aop;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.agent.config.InitCase;
import com.jd.jr.pmock.agent.jvmScript.ScriptRun;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * mock对象执行时，返回的数据对象
 * User: yangkuan@jd.com
 * Date: 18-5-24
 * Time: 下午1:33
 */
public class AopProxyHandler {

    public static <T> T afterInvoke0(String targetMethodName, Object[] targetMethodArgs, Object targetObject) throws Exception {
            return (T) afterInvoke2(targetMethodName,targetMethodArgs,targetObject,null,null);
    }
    public static <T> T afterInvoke1(String targetMethodName, Object[] targetMethodArgs, Object targetObject,String realClassSimpleName) throws Exception {
        return (T) afterInvoke2(targetMethodName, targetMethodArgs, targetObject, realClassSimpleName, null);
    }
    public static <T> T afterInvoke2(String targetMethodName, Object[] targetMethodArgs, Object targetObject, String realClassSimpleName,Method method) throws Exception {
        CaseJdkMethodInfo caseMethodVo = caseMethod(targetObject, targetMethodName, realClassSimpleName);
        wrapCaseMethodVo(caseMethodVo,method);
        String jsonReponse = (String) ScriptRun.runScript(caseMethodVo.caseSctript, targetMethodName, targetMethodArgs,null);
        if(caseMethodVo.isString ){
            return (T) jsonReponse;
        }
        if (caseMethodVo.returnTypeIsGeneric) {
            return (T) JSON.parseObject(jsonReponse, caseMethodVo.type);
        } else {
            return (T) JSON.parseObject(jsonReponse, caseMethodVo.returnClass);
        }
    }

    /**
     * javassist在传递时，会擦掉泛型。通过jdk的Method，补偿泛型封装
     * @param caseMethodVo
     * @param method
     */
    private static void wrapCaseMethodVo(CaseJdkMethodInfo caseMethodVo, Method method){
        caseMethodVo.returnClassName = caseMethodVo.getClass().getName();
        if(caseMethodVo.returnClass.getName().equals("java.lang.String")){
            caseMethodVo.isString = true;
        }
        if(method==null)
            return;
        if(!caseMethodVo.returnTypeIsGeneric){
            caseMethodVo.returnClass = method.getReturnType();
            Type type = method.getGenericReturnType();
            if(type!=null&&type instanceof ParameterizedType){
                caseMethodVo.returnTypeIsGeneric = Boolean.TRUE;
                caseMethodVo.type = type;
            }
        }
    }
    /**
     * 返回执行aop的方法详情
     *
     * @param targetObject
     * @param targetMethodName
     * @param realClassSimpleName
     * @return
     */
    private static CaseJdkMethodInfo caseMethod(Object targetObject, String targetMethodName, String realClassSimpleName) {
        CaseJdkMethodInfo caseMethodVo = new CaseJdkMethodInfo();
        Class clazz = targetObject.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals(targetMethodName)) {
                caseMethodVo.returnClass = method.getReturnType();
                Type type = method.getGenericReturnType();
                if(type!=null&&type instanceof ParameterizedType){
                    caseMethodVo.returnTypeIsGeneric = Boolean.TRUE;
                    caseMethodVo.type = type;
                }
                String classSimpleName = clazz.getSimpleName();
                String caseSctript = InitCase.getCase(classSimpleName, methodName);
                if (caseSctript == null) {
                    caseSctript = InitCase.getCase(realClassSimpleName, methodName);
                }
                if (caseSctript == null) {
                    System.out.println(methodName + "方法未找到case脚本");
                }
                caseMethodVo.caseSctript = caseSctript;
                break;
            }
        }
        return caseMethodVo;
    }


}
