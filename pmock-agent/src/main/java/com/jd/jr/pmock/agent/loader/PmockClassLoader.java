package com.jd.jr.pmock.agent.loader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-30
 * Time: 下午1:41
 */
public class PmockClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
            InputStream inputStream = getClass().getResourceAsStream(fileName);
            if(inputStream==null){//仍然使用上级加载器加载，没有破坏双亲委托
                return super.loadClass(name);
            }
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            return defineClass(name,b,0,b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
   public  Class<?>  loadClass(String name,byte[] b) throws ClassNotFoundException {
       try {
           return defineClass(name,b,0,b.length);
       } catch (Exception e) {
           throw new ClassNotFoundException(name);
       }
    }



}
