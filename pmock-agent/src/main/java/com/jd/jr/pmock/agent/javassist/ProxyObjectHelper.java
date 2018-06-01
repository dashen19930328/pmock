package com.jd.jr.pmock.agent.javassist;

import com.jd.jr.pmock.agent.aop.AopProxyHandler;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-30
 * Time: 下午10:27
 */
public class ProxyObjectHelper {
    public static <T> T getProxyOject(Class mockObject) {
        Object proxyObject = null;
        try {
            //代理对象
            ProxyFactory factory = new ProxyFactory();
            if (!mockObject.isInterface()) {
                //设定需要代理的类
                factory.setSuperclass(mockObject);
            } else {
                //设定需要代理的接口
                Class[] ifs = new Class[]{mockObject};
                factory.setInterfaces(ifs);
            }
            //创建class
            Class<?> clazz = factory.createClass();
            //实例化对象
            proxyObject =  clazz.newInstance();
            //设置代理对象
            ((ProxyObject)proxyObject).setHandler(new MethodHandler() {
                /**
                 * @param obj 原来的对象
                 * @param method  这个是原来类的方法
                 * @param process JavaAssist获取的进程，是修改后的类方法
                 * @param args 参数
                 */
                @Override
                public Object invoke(Object obj, Method method, Method process, Object[] args) throws Throwable {
                    return AopProxyHandler.afterInvoke2(method.getName(), args, obj, obj.getClass().getSimpleName().split("_")[0], method);
                }
            });
        } catch (Exception e) {
            System.out.println("ProxyObjectHelper代理对象异常:" + e.getMessage());
        }
        return (T)proxyObject;
    }
}
