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



}
