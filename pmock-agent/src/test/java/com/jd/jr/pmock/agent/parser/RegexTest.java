package com.jd.jr.pmock.agent.parser;


import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 上午9:42
 */
public class RegexTest {

    public static void main(String[] args) {
        String content = "   def   queryPersonMap(paraObj) {";
        String pattern = "\\s*+def .*";

        boolean isMatch = Pattern.matches(pattern, content);

        System.out.println("去掉了空格，字符串中是否包含了 'def'开头的子字符串? " + isMatch);


        // 现在创建 matcher 对象
        String pattern1 = "\\s?def\\s(.*?)\\(";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m = r1.matcher(content);
        if (m.find()) {
            System.out.println("Found value|" + m.group(1).replace(" ", ""));
        } else {
            System.out.println("NO MATCH");
        }
    }

    @Test
   public void mainTest(){
        String content = "static   main(def args)";
        String pattern = ".*\\s+main\\s*\\(.*";//main前面至少有一个空格，后面至少有一个空格或者紧贴着(

        boolean isMatch = Pattern.matches(pattern, content);

        System.out.println("字符串中是否包含了 'main'的子字符串? " + isMatch);


        String pattern2 = ".*static.*main.*";//main前面至少有一个空格，后面至少有一个空格或者紧贴着(

          isMatch = Pattern.matches(pattern2, content);

        System.out.println("字符串中是否包含了 'static'的子字符串? " + isMatch);



        // 现在创建 matcher 对象
        String pattern1 = "main";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m = r1.matcher(content);
        while( m.find() )
        {
            System.out.println(m.group());
        }
    }

    @Test
    public void otherTest(){
        String content =
                "  def jsonSlurper = new JsonSlurper();";//以0个或者多个空格开始，def+空格结束//示例: "   def   queryPersonMap(paraObj) {"
        String pattern = "\\s*+def .*";

        boolean isMatch = Pattern.matches(pattern, content);

        System.out.println("去掉了空格，字符串中是否包含了 'def'开头的子字符串? " + isMatch);


        // 现在创建 matcher 对象
        String pattern1 = "\\s?def\\s(.*?)\\(";//提取def 和( 之间的字符串。以def为开头，但def前面或者后面可以允许有多个空格
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m = r1.matcher(content);
        if (m.find()) {
            System.out.println("Found value|" + m.group(1).replace(" ", ""));
        } else {
            System.out.println("NO MATCH");
        }
    }
}
