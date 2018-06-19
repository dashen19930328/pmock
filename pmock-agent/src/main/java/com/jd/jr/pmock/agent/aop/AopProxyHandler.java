package com.jd.jr.pmock.agent.aop;

import com.alibaba.fastjson.JSON;
import com.jd.jr.pmock.agent.config.InitCase;
import com.jd.jr.pmock.agent.jvmScript.ScriptRun;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

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
        Object jsonReponse =  ScriptRun.runScript(caseMethodVo.caseSctript, targetMethodName, targetMethodArgs,caseMethodVo.scriptType);
/*        if(caseMethodVo.isString ){
            return (T) jsonReponse;
        }*/
        //String returnClassName = jsonReponse.getClass().getName();
        String returnClassName = caseMethodVo.returnClass.getName();
        if( returnClassName.equals("java.lang.String")||
                returnClassName.equals("java.lang.Integer")||returnClassName.equals("int") ||
                returnClassName.equals("java.lang.Long")||returnClassName.equals("long")||
        returnClassName.equals("java.lang.Double")||returnClassName.equals("double") ){
            return (T) jsonReponse;
        }
        if (caseMethodVo.returnTypeIsGeneric) {
            return (T) JSON.parseObject((String)jsonReponse, caseMethodVo.type);
        } else {
            return (T) JSON.parseObject((String)jsonReponse, caseMethodVo.returnClass);
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
                Map<String, String>  caseConfigMap = InitCase.getCase(classSimpleName);
                if (caseConfigMap == null) {
                    caseConfigMap = InitCase.getCase(realClassSimpleName);
                }
                if (caseConfigMap == null) {
                    System.out.println(realClassSimpleName + "未找到case脚本");
                }
                caseMethodVo.caseSctript = caseConfigMap.get("scriptText");
                caseMethodVo.scriptType = caseConfigMap.get("scriptType");
                break;
            }
        }
        return caseMethodVo;
    }


}
