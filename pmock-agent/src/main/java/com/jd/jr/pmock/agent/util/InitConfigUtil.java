package com.jd.jr.pmock.agent.util;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午3:09
 */
public class InitConfigUtil {
    private static String spockInitConfig = "pmock/initConfig.properties";

    public static Map<String, String> config = new HashMap<String, String>();

    public static void init() {
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(InitConfigUtil.class.getResourceAsStream("/" + spockInitConfig), "UTF-8"));
            Enumeration<String> keyEnum = (Enumeration<String>) properties.propertyNames();
            while (keyEnum.hasMoreElements()) {//Properties 的get是线程安全的，所以转换用不加锁的
                String key = keyEnum.nextElement();
                config.put(key, properties.getProperty(key));
            }
        } catch (Exception e) {
            System.out.println("pmock启动，读取初始配置文件失败:" + e.getMessage());
        }
    }

    public static String get(String key) {
        if (config == null || config.size() == 0) {
            init();
        }
        return config.get(key);
    }

    public static String get(String key,String path) {
        if (config == null || config.size() == 0) {
            init();
        }
        return config.get(key);
    }
    public static String getVal(String key,String path) {
        try {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(InitConfigUtil.class.getResourceAsStream("/" + path), "UTF-8"));
           return properties.getProperty(key);
        } catch (Exception e) {
            System.out.println("pmock启动，读取初始配置文件失败:" + e.getMessage());
            return null;
        }
    }
}
