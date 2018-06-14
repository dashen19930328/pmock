package com.jd.jr.pmock.agent.jvmScript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptRun {

   private static ScriptEngineManager factory = new ScriptEngineManager();
    /**
     * @param scriptText  要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName 要执行的方法名
     * @param params  执行grovvy需要传入的参数
     * @param scriptType
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public static Object runScript(String scriptText, String funName, Object[] params,String scriptType) {
        try {
            if(scriptType==null||scriptType.equals("")){
                scriptType = "groovy";
            }
            ScriptEngine engine = factory.getEngineByName(scriptType);
            //long start = System.currentTimeMillis();
            engine.eval(scriptText);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
            return inv.invokeFunction(funName, params);
        } catch (Exception e) {
            System.out.println("脚本类型:"+scriptType+",执行ScriptRun的runScript异常:"+e.getMessage());
            return null;
        }
    }

    /**
     * @param scriptText  要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName 要执行的方法名
     * @param params  执行grovvy需要传入的参数
     * @param scriptType
     * @return
     * @desc 执行groovy脚本(需要指定方法名)
     */
    public static void runMain(String scriptText, String funName, Object[] params,String scriptType) {
        try {
            if(scriptType==null||scriptType.equals("")){
                scriptType = "groovy";
            }
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName(scriptType);
            //long start = System.currentTimeMillis();
            engine.eval(scriptText);//线程里首次执行，会较慢
            //System.out.println("执行groovy脚本："+(System.currentTimeMillis()-start));
            Invocable inv = (Invocable) engine;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("脚本类型:"+scriptType+",执行ScriptRun的runMain异常:"+e.getMessage());
        }
    }
}
