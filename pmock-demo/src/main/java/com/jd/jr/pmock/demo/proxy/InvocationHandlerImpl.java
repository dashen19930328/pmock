package com.jd.jr.pmock.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * User: yangkuan@jd.com
 * Date: 15-1-30
 * Time: 下午5:55
 */
public class InvocationHandlerImpl<T> implements InvocationHandler {
    private Map<Method, HttpClientInvoker> methodMap;
    private Class<T> t;

    public InvocationHandlerImpl(Class<T> t, Map<Method, HttpClientInvoker> methodMap) {
        this.t = t;
        this.methodMap = methodMap;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("toString") && (args == null || args.length == 0))
        {
            return this.toString();
        }
        if (methodMap.get(method) != null) {
            return null;//20180601先阉割掉
           /* if (args != null && args.length > 0) {
                Object obj = methodMap.get(method).invoke(args[0]);
                return obj;
            } else {
                Object obj = methodMap.get(method).invoke(null);
                return obj;
            }*/
        }
        return null;
    }



    public Class<T> getT() {
        return t;
    }

    public String toString()
    {
        return t.getName()+"_proxy";
    }
}
