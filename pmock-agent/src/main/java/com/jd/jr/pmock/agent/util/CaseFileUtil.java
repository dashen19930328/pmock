package com.jd.jr.pmock.agent.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午2:57
 */
public class CaseFileUtil {
    public static String readToString(File file) {
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent, encoding);
        } catch (Exception e) {
            System.out.println("file name:" + file.getName() + "read file :" + e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主要涉及解析groovy脚本的方法
     *
     * @param caseString
     * @return
     */
    public static Map<String, String> readCaseText(String caseString) {
        Map<String, String> caseMethodMap = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new StringReader(caseString));
            String lineTxt = null;
            StringBuilder methodContext = new StringBuilder();
            String beforeMethodName = "";
            while ((lineTxt = br.readLine()) != null) {
                if (isMainFunction(lineTxt)) {
                    caseMethodMap.put(beforeMethodName, methodContext.toString());
                    beforeMethodName = "main";
                    methodContext = new StringBuilder();
                    methodContext.append(lineTxt);
                }
                String pattern = "\\s*+def .*";//以0个或者多个空格开始，以def+空格结束//示例: "   def   queryPersonMap(paraObj) {"
                boolean isMatch = Pattern.matches(pattern, lineTxt);
                if (isMatch) {
                    String extractPattern = "\\s?def\\s(.*?)\\(";//提取def 和( 之间的字符串。以def为开头，但def前面或者后面可以允许有多个空格
                    Pattern extractRegex = Pattern.compile(extractPattern);
                    Matcher m = extractRegex.matcher(lineTxt);
                    if (m.find()) {//读取到后面的方法，就把内容算作前面方法
                        caseMethodMap.put(beforeMethodName, methodContext.toString());
                        beforeMethodName = m.group(1).replace(" ", "");//去掉方法名字的前后所有空格
                        methodContext = new StringBuilder();
                        methodContext.append(lineTxt);
                    }
                } else {
                    methodContext.append(lineTxt);
                }
            }
            caseMethodMap.put(beforeMethodName, methodContext.toString());//最后一个方法
            br.close();
        } catch (Exception e) {
            System.out.println("read errors :" + e);
        }
        return caseMethodMap;
    }


    /**
     * 是否包含main函数
     *
     * @return
     */
    private static boolean isMainFunction(String content) {
        String pattern = ".*\\s+main\\s*\\(.*";//main前面至少有一个空格，后面至少有一个空格或者紧贴着(
        boolean isMatch = Pattern.matches(pattern, content);
        if (!isMatch) return false;
        String pattern2 = ".*static.*main.*";//必须同时包含static main

        isMatch = Pattern.matches(pattern2, content);
        return isMatch;
    }

}
