package com.jd.jr.pmock.agent.groovy;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-23
 * Time: 下午2:50
 */
public class GroovyRun {

    /**
     * @param script  要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName 要执行的方法名
     * @param params  执行grovvy需要传入的参数
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public Object runGroovyScript(String script, String funName, Object[] params) {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            //long start = System.currentTimeMillis();
            engine.eval(script);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
            return inv.invokeFunction(funName, params);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行GroovyRun的runGroovyScript异常:"+e.getMessage());
            return null;
        }
    }


}
