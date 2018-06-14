package com.jd.jr.pmock.agent.jvmScript;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-13
 * Time: 下午2:17
 */
public class ScriptRunTest {
    @Test
    public void runGroovyScriptTest(){
        String text = "import groovy.json.JsonSlurper\n" +
                "def queryCountInter(count){\n" +
                "    println count;\n" +
                "    return \"9\";\n" +
                "}\n" +
                "public static void main(def args){\n" +
                "    def jsonSlurper = new JsonSlurper();\n" +
                "    def map = jsonSlurper.parseText('{\"name\":\"queryAge\"}');\n" +
                "    println queryCountInter(1) ; \n" +
                "}";
      Object response = ScriptRun.runScript(text, "main", null,null);
System.out.println(response);
    }


    @Test
    public void runJavaScriptTest(){
        String text ="function execute(s1, s2){\n" +
                "  return s1 + s2;\n" +
                "}";
        String response = (String) ScriptRun.runScript(text, "execute", new String[]{"1","2"},"js");
        System.out.println(response);
        Double response1 = (Double)ScriptRun.runScript(text, "execute", new Integer[]{1,2},"js");
        System.out.println(response1);
    }


    @Test
    public void runPythonScriptTest(){
        String text ="def printme( str ):\n" +
                "   \"python==\"\n" +
                "   print str\n" +
                "   return \"python return\"";
        String response = (String) ScriptRun.runScript(text, "printme", new String[]{"testpython"},"python");
        System.out.println(response);
    }
    @Test
    public void runRubyScriptTest(){
        String text ="def test(a1 , a2 )\n" +
                "   puts \"ruby  #{a1}\"\n" +
                "   puts \"ruby #{a2}\"\n" +
                "   return a1+a2\n" +
                "end";
        String response = (String) ScriptRun.runScript(text, "test", new String[]{"testRuby1","testRuby2"},"ruby");
        System.out.println(response);
        Object response1 =  ScriptRun.runScript(text, "test", new Integer[]{1,2},"ruby");
        System.out.println(response1);
    }
    @Test
    public  void testScriptEngine(){
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine scriptEngine = factory.getEngineByName("js");
        System.out.println("js="+scriptEngine);
          scriptEngine = factory.getEngineByName("JavaScript");
        System.out.println("JavaScript="+scriptEngine);

        scriptEngine = factory.getEngineByName("Groovy");
        System.out.println("Groovy="+scriptEngine);

        scriptEngine = factory.getEngineByName("groovy");
        System.out.println("groovy="+scriptEngine);
        scriptEngine = factory.getEngineByName("jython");
        System.out.println("jython="+scriptEngine);
        scriptEngine = factory.getEngineByName("python");
        System.out.println("python="+scriptEngine);
        scriptEngine = factory.getEngineByName("jruby");
        System.out.println("jruby="+scriptEngine);
        scriptEngine = factory.getEngineByName("ruby");
        System.out.println("ruby="+scriptEngine);
        scriptEngine = factory.getEngineByName("scala");
        System.out.println("scala="+scriptEngine);
    }
}
