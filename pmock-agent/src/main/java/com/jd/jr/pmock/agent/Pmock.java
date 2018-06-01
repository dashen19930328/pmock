package com.jd.jr.pmock.agent;

import java.lang.reflect.Field;

public class Pmock {

    static ThreadLocal<PmockFunction> aplicationContext = new ThreadLocal<PmockFunction>();//多线程并发测试。保存线程现场的实例

    public static <T> T mock(Class mockObject)  {
        PmockFunction pmockFunction = new PmockFunction();
        return pmockFunction.mock(mockObject);
    }


    /**
     * 将mock对象赋值给测试对象
     * @param mockObject
     * @param mockFieldName
     * @param targetObject
     * @throws Exception
     */
    public static <T> T  mockField(Object mockObject, String mockFieldName , T targetObject)   {
        try{
        Field field = targetObject.getClass().getDeclaredField(mockFieldName);
        field.setAccessible(true);
        field.set(targetObject, mockObject);
        }catch (Exception e){
            String mockName = "null";
            if(mockObject!=null){
                mockName =mockObject.getClass().getName();
            }
            System.out.println("将mock对象:"+mockName+",赋值给类: " + targetObject.getClass().getSimpleName() + "的属性" + mockFieldName + ",异常:" + e.getMessage());
        }
        return targetObject;
    }

    /**
     * mock对象
     * @param mockObject
     * @return
     */
    public static PmockFunction mockObject(Class mockObject)   {
        return getThreadPmockFunction().mockObject(mockObject);
    }

    /**
     * mock测试类
     * @param mockObject
     * @return
     */
    public static PmockFunction mockTarget(Class mockObject)   {
        try {
            return getThreadPmockFunction().mockTarget(mockObject.newInstance());
        } catch (Exception e) {
            System.out.println("Pmock mockObject newInstance,异常:" + e.getMessage());
            return null;
        }
    }

    /**
     * mock测试类
     * @param targetObject
     * @return
     */
    public static PmockFunction mockTarget(Object targetObject)   {
               return getThreadPmockFunction().mockTarget(targetObject);
    }

    private static PmockFunction getThreadPmockFunction(){
        PmockFunction pmockFunction = aplicationContext.get();
        if(pmockFunction==null){
            pmockFunction = new PmockFunction();
            aplicationContext.set(pmockFunction);
        }
        return pmockFunction;
    }
}
