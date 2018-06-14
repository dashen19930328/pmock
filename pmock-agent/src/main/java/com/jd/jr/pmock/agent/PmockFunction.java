package com.jd.jr.pmock.agent;

import com.jd.jr.pmock.agent.javassist.ProxyObjectHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PmockFunction  {
    private PmockFunction pmock;
    private Object targetObject;
    private Stack<Object> mockObjectStack;
    private Stack<String> mockFieldStack;//mockFieldStack需要和mockObjectStack 大小一致
    private List<Object> targetObjectList;//每个PmockFunction实例里，targetObject最多只能有一个。
    public <T> T mock(Class mockObject) {
        //  if(mockObject.isInterface() ){
        //return  JavassistHelper.getProxyOject(mockObject);
        return (T)ProxyObjectHelper.getProxyOject(mockObject);
/*   }else{
        return JavassistHelper.getNewOject(mockObject);//担心不同classloader加载的，到前台转型异常。
      }*/
    }

    public <E> E target(){
        return (E) this.targetObject;
    }


    @Deprecated
    public PmockFunction mockField(Object targetObject, String mockFieldName) {
        if (this.targetObject == null) {
            this.targetObject = targetObject;
        }
        return this.mockField(mockFieldName);
    }

    public PmockFunction mockField(String mockFieldName) {
        Object  mockObject = null;
        try {
            if(mockFieldStack==null){
                mockFieldStack = new Stack<String>();
            }
            mockFieldStack.push(mockFieldName);
            existStack(mockFieldName);
            mockObject = mockObjectStack.pop();
            Field field = this.targetObject.getClass().getDeclaredField(mockFieldStack.pop());
            field.setAccessible(true);
            field.set(this.targetObject, mockObject);
        } catch (Exception e) {
            String mockName = "null";
            if(mockObject!=null){
                mockName =mockObject.getClass().getName();
            }
            String targetObjectName = "null";
            if(targetObject!=null){
                targetObjectName =targetObject.getClass().getName();
            }
            String info = "将mock对象:"+mockName+",赋值给测试类: " + targetObjectName + "的属性" + mockFieldName + ",报错。异常信息如下:" + e.getMessage();
            System.out.println(info);
            throw  new RuntimeException(e);
        }
        return pmock=this;
    }

    private void existStack(String mockFieldName){
        if (this.targetObject == null) {
            String info = "实例里没有要测试的对象，请先设置测试对象";
            throw new RuntimeException(info);
        }
        existMockObjectStack();
        //existMockFieldStack(mockFieldName );
    }

    private void existMockFieldStack(String mockFieldName){

        if(mockFieldStack.size()!=mockObjectStack.size()){
            String info = "属性字段"+mockFieldName+"没有对应的mock对象";
            throw new RuntimeException(info);
        }
    }
    private void existMockObjectStack(){
        if(mockObjectStack==null||mockObjectStack.isEmpty()){
            String info = "实例里没有mock对象，请先设置mock对象";
            throw new RuntimeException(info);
        }
    }

    public PmockFunction mockObject(Class mockObject) {
        if (mockObjectStack == null) {
            mockObjectStack = new Stack<Object>();
        }
        mockObjectStack.push(this.mock(mockObject));
        return pmock=this;
    }

    /**
     * 设置要测试的对象。同一个实例里，只会设置一个。
     *
     * @param targetObject
     * @return
     */
    public PmockFunction mockTarget(Class targetObject) {
        try{
      return mockTarget(targetObject.newInstance());
        }catch (Exception e){
            System.out.println("PmockFunction mockObject newInstance,异常:" + e.getMessage());
            return null;
        }
    }

    /**
     * 设置要测试的对象。同一个实例里，只会设置一个。
     *
     * @param targetObject
     * @return
     */
    public PmockFunction mockTarget(Object targetObject) {
        if (this.targetObject == null) {
            this.targetObject = targetObject;
            targetObjectList = new ArrayList<Object>();
        }
        warning(targetObject);
        return pmock=this;
    }
    private void warning(Object targetObject){
        targetObjectList.add(targetObject);
        if(targetObjectList.size()>1){
            String targetObjectName = "null";
            if(targetObject!=null){
                targetObjectName = targetObject.getClass().getSimpleName();
            }
            System.out.println("提醒:mockTarget只能被设置一次，无效的设置，类名:"+targetObjectName);
        }
    }
}
