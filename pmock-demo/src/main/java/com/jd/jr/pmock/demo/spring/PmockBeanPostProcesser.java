package com.jd.jr.pmock.demo.spring;

import com.jd.jr.pmock.agent.PmockFunction;
import com.jd.jr.pmock.agent.config.InitCase;
import javassist.CtClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-26
 * Time: 下午2:01
 */
public class PmockBeanPostProcesser implements BeanPostProcessor {
    private String testType;
    private Map map = new ConcurrentHashMap(100);
    private static final Logger log = LoggerFactory.getLogger("myBeanPostProcesser");

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(testType!=null&&testType.equals("mock")){
            String className = bean.getClass().getName();
            String simpleName = bean.getClass().getSimpleName();
            //com.jd.jr.pmock.demo.rpc.PlayRpc_proxy
             if (bean.toString().contains("_proxy")) {
                 className = bean.toString().split("_")[0];
                 simpleName = className.substring(className.lastIndexOf(".") + 1);
            }
            if (map.get(beanName) != null) {
                log.info(beanName + "已经代理过,不进行再次代理!");
                return map.get(beanName);
            }
            if (InitCase.classIsConfig(simpleName)){
               log.info("需要mock的类名："+simpleName);
                PmockFunction pmockFunction = new PmockFunction();
                try {
                    Class mockClass = getclass(className);
                   Object proxyBean =   pmockFunction.mock(mockClass);
                    map.put(beanName,proxyBean);
                    return proxyBean;
                } catch (Exception e) {
                    log.error("PmockBeanPostProcesser getclass 异常:",e.getMessage());
                }
            }
        }
        return bean;
    }

    private static Class getclass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException//className是类名
    {
        Class obj=Class.forName(className); //以String类型的className实例化类
        return obj;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}
