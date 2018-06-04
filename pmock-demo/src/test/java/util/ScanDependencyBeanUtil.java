package util;

import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * User: yangkuan@jd.com
 * Date: 15-1-27
 * Time: 下午8:11
 */
class BeanNames {
    public String appointName;//指定的名字
    public String className;//全路径名字
    public String simpleName;//简写名字
    public String varName;//变量名字
    public String appointVarName;//类名的首字母小写
}

public class ScanDependencyBeanUtil {

    @Test
    public void main() throws Exception {
        Long startTime = System.currentTimeMillis();
        //①扫描出WEB-INF下本系统的所有在类注解有service的类路径
        // 需要配置includePackge
        //②扫描出指定的注入class依赖bean   需要配置 scanClasses
        Map<String, String> servicePathMap = new HashMap<String, String>();
        //取得整个工程里,指定包下面的标记service注解的类。包括jar包
        scanServicePathMap(servicePathMap,"com.jd.jr.pmock.demo.dao");
        //指定要扫描的类，他们要注入的bean
        Class[] scanClasses = new Class[]{PersonBusinessServiceImpl.class};
        Map<String, Class> hasLoanBean = new HashMap<String, Class>();
        List<BeanNames> allBeanList =
                getAllDependencyBean(scanClasses, servicePathMap, hasLoanBean);
        List<BeanNames> selfSystemBean = new ArrayList<BeanNames>();
        //打印依赖的bean
        getSelfSystemBeanList(selfSystemBean,servicePathMap,allBeanList);
        //包装内部依赖bean的xml,rpc bean的xml配置比较个性，需要手工配置
        wrapBeanXml(selfSystemBean, servicePathMap,scanClasses);
        System.out.println("耗时毫秒："+(System.currentTimeMillis()-startTime));

        System.out.println("请将相关的依赖，配置到单独的配置文件里，进行spring容器启动单独加载。可以敦促代码精简和重构");
    }

    public void getSelfSystemBeanList( List<BeanNames> selfSystemBean ,  Map<String, String> servicePathMap,  List<BeanNames> allBeanList ){
        System.out.println("依赖的bean数量："+allBeanList.size());
        List<String> otherSystemBean = new ArrayList<String>();
        Boolean isDB = false;
        for (BeanNames beanNames : allBeanList) {
            String appointName = beanNames.appointName;
            String className = beanNames.className;
            String varName = beanNames.varName;
            String simpleName = beanNames.simpleName;
            String appointVarName = beanNames.appointVarName;

            if (servicePathMap.get(appointName) != null ||
                    servicePathMap.get(className) != null ||
                    servicePathMap.get(varName) != null ||
                    servicePathMap.get(simpleName) != null||
                    servicePathMap.get(appointVarName) != null) {
                //System.out.println(simpleName);
                selfSystemBean.add(beanNames);
            /*    if (beanNames.className.contains("InvestOrderAccountTradeResourceExport")) {
                    System.out.println();
                }*/
            } else {
             /*   if (beanNames.className.contains("InvestOrderAccountTradeResourceExport")) {
                    System.out.println();
                }*/
                if (beanNames.className.endsWith("Mapper")) {
                    isDB = true;
                    continue;
                }
                if (beanNames.className.endsWith("PlatformTransactionManager")) {
                    isDB = true;
                    continue;
                }                    //System.out.println("外部系统bean:" + beanNames.simpleName);
                    otherSystemBean.add(beanNames.simpleName);
            }
        }
/*        System.out.println(selfSystemBean.size());
        System.out.println(selfSystemBean);
        System.out.println(otherSystemBean.size());*/
        System.out.println("依赖的外部系统bean，数量="+otherSystemBean.size()+",\n"+otherSystemBean.toString());
        if(isDB){
            System.out.println("需要加载数据库的相关配置");
        }

    }
    public void wrapBeanXml(List<BeanNames> selfSystemBean, Map<String, String> servicePathMap, Class[] scanClasses) {
        StringBuffer sb = new StringBuffer("");
        for (BeanNames beanNames : selfSystemBean) {
            sb.append("<bean id=\"");
            String beanNameId = beanNames.simpleName.substring(0, 1).toLowerCase() + beanNames.simpleName.substring(1);
            sb.append(beanNameId);
            sb.append("\" class=\"");
            String appointName = beanNames.appointName;
            String className = beanNames.className;
            String varName = beanNames.varName;
            String simpleName = beanNames.simpleName;
            String appointVarName = beanNames.appointVarName;
            String beanClassPath;
            if (servicePathMap.get(appointName) != null) {
                beanClassPath = servicePathMap.get(appointName);
            } else if (servicePathMap.get(className) != null) {
                beanClassPath = servicePathMap.get(className);

            } else if (servicePathMap.get(varName) != null) {
                beanClassPath = servicePathMap.get(varName);
            }  else if (servicePathMap.get(appointVarName) != null) {
                beanClassPath = servicePathMap.get(appointVarName);
            }else {
                beanClassPath = servicePathMap.get(simpleName);
            }
            sb.append(beanClassPath);
            sb.append("\"/>");
            sb.append("\n");
        }
        for (Class classIntance : scanClasses) {
            sb.append("<bean id=\"");
            String beanNameId = classIntance.getSimpleName().substring(0, 1).toLowerCase() + classIntance.getSimpleName().substring(1);
            sb.append(beanNameId);
            sb.append("\" class=\"");
            sb.append(classIntance.getName());
            sb.append("\"/>");
            sb.append("\n");
        }
        System.out.println("内部依赖的bean配置,数量="+selfSystemBean.size()+",\n"+sb.toString());
    }

    public List<BeanNames> getAllDependencyBean(Class[] scanClasses, Map<String, String> servicePathMap, Map<String, Class> hasLoanBean) {
        List<BeanNames> allBeanList = new ArrayList<BeanNames>();
        for (Class classIntance : scanClasses) {
            if(classIntance.getName().contains("AccountService")){
               // System.out.println();
            }
            hasLoanBean.put(classIntance.getName(), classIntance);
            Class<?> t = classIntance;
            Field[] fields = t.getDeclaredFields();
            for (Field field : fields) {
                String beanSimpleName = field.getType().getSimpleName();
                String beanAllpathName = field.getType().getName();
                String varName = field.getName();
                String appointName = null;
                String appointVarName = beanSimpleName.substring(0, 1).toLowerCase() + beanSimpleName.substring(1);
                Annotation[] fieldAnnotations = field.getAnnotations();
                for (Annotation annotation : fieldAnnotations) {

                    String annotationName = annotation.annotationType().getSimpleName();

                    if (beanAllpathName.contains("AccountService")) {
                    //    System.out.println(annotation.annotationType().getSimpleName());
                    }
                    //System.out.println(annotation.annotationType());
                    if (annotationName.equals("Resource") || annotationName.equals("Autowired")) {//TODO 应该进一步判断Resource 是否有注解name
                        if (hasLoanBean.get(beanAllpathName) != null) {
                            continue;
                        } else {
                            hasLoanBean.put(beanAllpathName, t);
                        }
                        if (annotationName.equals("Resource")) {
                            Resource resource = field.getAnnotation(Resource.class);
                            appointName = resource.name();
                        }
                        BeanNames beanNames = new BeanNames();
                        beanNames.simpleName = beanSimpleName;
                        beanNames.className = beanAllpathName;
                        beanNames.varName = varName;
                        beanNames.appointName = appointName;
                        beanNames.appointVarName = appointVarName;
                        //  System.out.println(beanName);
                        allBeanList.add(beanNames);

                        String beanClassName = servicePathMap.get(beanSimpleName);
                        if (servicePathMap.get(appointName) != null) {
                            beanClassName = servicePathMap.get(appointName);
                        } else if (servicePathMap.get(beanAllpathName) != null) {
                            beanClassName = servicePathMap.get(beanAllpathName);
                        } else if (servicePathMap.get(varName) != null) {
                            beanClassName = servicePathMap.get(varName);
                        }  else if (servicePathMap.get(appointVarName) != null) {
                            beanClassName = servicePathMap.get(appointVarName);
                        }
                        if (beanClassName != null) {
                            try {
                                Class newBean = Class.forName(beanClassName);
                                Class[] dependencyBeanClass = new Class[]{newBean};
                                allBeanList.addAll(getAllDependencyBean(dependencyBeanClass, servicePathMap, hasLoanBean));
                            } catch (Exception e) {
                                System.out.println("加载依赖bean报错:" + e.getMessage());
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return allBeanList;
    }


    public void scanServicePathMap(Map<String, String> servicePathMap,String includePackge) {

        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);
        String path = f.getParentFile().getAbsolutePath();
        List<String> classNames = getClassNameByFile(path, includePackge, true);
        if (classNames != null) {
            for (String className : classNames) {
                // System.out.println(className);
                try {
                    Class<?> t = Class.forName(className);
                    Annotation[] classAnnotations = t.getAnnotations();
/*                    if (className.contains("MatchStockResource")) {
                        System.out.println(className);
                    }*/
                    for (Annotation annotation : classAnnotations) {
                        if (annotation instanceof Service) {
                            Service service = t.getAnnotation(Service.class);
                            //  System.out.println(service.value());
                            String serviceName = service.value();//如果指定了serviceName，则map注入保存指定名字的bean
        /*                if(serviceName!=null&&!serviceName.equals("")){
                            System.out.println(serviceName);
                        }*/

                            servicePathMap.put(className, className);//①全路径保存一份。防止有人注入时，直接使用具体类全路径为key。
                            servicePathMap.put(t.getSimpleName(), className);//②具体类的名字为key，保存一份。
                            if (serviceName != null && !serviceName.equals("")) {//③指定注解的名字为key,保存一份
                                servicePathMap.put(serviceName, className);
                            }

                            Class<?>[] interfaces = t.getInterfaces();
                            if (interfaces != null && interfaces.length > 0) {//
                                for (Class interfacesClass : interfaces) {
                                    if (serviceName == null || serviceName.equals("")) {
                                        servicePathMap.put(interfacesClass.getName(), className);//如果注解没有指定名字，且有接口，每个接口全路径保存一份。
                                    }
                                }
                            }
                            continue;
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }

    }

    @Test
    public void showURL() throws IOException {

        // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);

        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
        File f2 = new File(this.getClass().getResource("").getPath());
        System.out.println(f2);

        // 第二种：获取项目路径    D:\git\daotie\daotie
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);


        // 第三种：  file:/D:/git/daotie/daotie/target/classes/
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath.getPath());


        // 第四种： D:\git\daotie\daotie
        System.out.println(System.getProperty("user.dir"));        /*
		 * 结果： C:\Documents and Settings\Administrator\workspace\projectName
		 * 获取当前工程路径
		 */

        // 第五种：  获取所有的类路径 包括jar包的路径
    System.out.println(System.getProperty("java.class.path"));

    }


    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath        文件路径
     * @param jarIncludePages
     * @param childPackage    是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, String jarIncludePages, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), jarIncludePages, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if(childFilePath.contains("test")){
                    continue ;
                }
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                } else if (childFilePath.endsWith(".jar")) {
                    String childFileName = childFile.getName();
                    // if(jarFiles.get(childFileName)!=null){
                    List<String> jarClassName = getClassNameByJar(childFilePath, jarIncludePages, true);
                    myClassName.addAll(jarClassName);
                    //    }
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
/*        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);*/
        String jarFilePath = jarPath;

        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    String classFullName = entryName.replace("/", ".");
                    if (childPackage) {
                        if (classFullName.contains(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, "", childPackage));
            }
        }
        return myClassName;
    }


}
