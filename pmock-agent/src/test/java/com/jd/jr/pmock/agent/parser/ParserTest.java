package com.jd.jr.pmock.agent.parser;

import com.jd.jr.pmock.agent.jvmScript.ScriptRun;
import org.junit.Test;

import java.util.Map;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-13
 * Time: 下午4:21
 */
public class ParserTest {
    @Test
    public void testGroovy(){
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
        Map<String, String> methodMap = GroovyCaseParser.parseCaseMethod(text);
        Object response = ScriptRun.runScript(methodMap.get("queryCountInter"), "queryCountInter", new String[]{"1"}, null);
        System.out.println(response);
    }

    @Test
    public void testJs(){
        String text ="function execute(s1, s2){\n" +
                "  return s1 + s2;\n" +
                "}";
        Map<String, String> methodMap = JsCaseParser.parseCaseMethod(text);
        Object response = ScriptRun.runScript(methodMap.get("execute"), "execute", new String[]{"1","2"}, "js");
        System.out.println(response);
    }
}
