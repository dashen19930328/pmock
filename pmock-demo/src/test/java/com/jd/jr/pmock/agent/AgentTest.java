package com.jd.jr.pmock.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jd.jr.pmock.agent.util.InitConfigUtil;
import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.dao.PersonBusinessDaoImpl;
import com.jd.jr.pmock.demo.service.PersonBusinessService;
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl;
import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Result;
import com.jd.jr.pmock.demo.vo.Shopping;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-23
 * Time: 下午8:32
 */
public class AgentTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        PersonQuery personQuery = new PersonQuery();
        personQuery.setAge(1);
        personQuery.setName("test");
        PersonBusinessDao personBusinessDao = new PersonBusinessDaoImpl();
        PersonBusinessService personBusinessService = new PersonBusinessServiceImpl();
        setField(personBusinessDao, personBusinessService);
        // System.out.println("queryAge:" + personBusiness.queryAge(personQuery).getAge());
        PersonVo person = personBusinessService.queryAge(personQuery);
        System.out.println("queryName:" + person.getName() + ",age:" + person.getAge());

        Map<String, PersonVo> personMap = personBusinessService.queryPersonMap(personQuery);
        System.out.println("personMap:" + personMap.get("aop").getName());
        Shopping shopping = personBusinessService.queryShopping(personQuery);
        System.out.println("shopping:" + shopping.getPerson().getName());
        Result<Shopping> shoppingResult = personBusinessService.queryShoppingResult(personQuery);
        System.out.println("shopping  泛型:" + shoppingResult.getValue().getPerson().getName());

        personBusinessService.queryVoid();
    }


    public void test() throws InvocationTargetException, IllegalAccessException {
        Class objclass = this.getClass();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            if (method.getName().equals("main")) {
                continue;
            }
            method.invoke(objclass, null);
        }
    }


    @Test
    public void testJson() {
        PersonVo person = new PersonVo();
        person.setName("查询人员名字");
        person.setSex(1);
        person.setAge(12);
        System.out.print(JSON.toJSON(person));
        Map<String, PersonVo> personMap = new HashMap<String, PersonVo>();
        personMap.put("key", person);
        System.out.println(JSON.toJSON(personMap));
        String text = JSON.toJSONString(personMap);
        //   Object jsonObj =   JSON.parseObject(JSON.toJSON(personMap),new TypeReference<HashMap<String,PersonVo>>(){});
        Object jsonObj = JSON.parseObject(text, new TypeReference<Map<String, PersonVo>>() {
        });
        // JSON.parseArray(text, Map<String, PersonVo>);

        System.out.println("");
        PersonBusinessDaoImpl personBusiness = new PersonBusinessDaoImpl();
        Result<Shopping> shoppingResult = personBusiness.queryShoppingResult(null);
        System.out.println(JSON.toJSONString(shoppingResult));
    }

    private static void setField(PersonBusinessDao personBusinessDao, PersonBusinessService personBusinessService) throws Exception {
        Field field = personBusinessService.getClass().getDeclaredField("personBusinessDao");
        field.setAccessible(true);
        field.set(personBusinessService, personBusinessDao);
    }

    @Test
    public void testInitConfig() {
        InitConfigUtil.init();
        Map<String, String> config = InitConfigUtil.config;
        System.out.println(config);
    }
}