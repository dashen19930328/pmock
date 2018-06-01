package com.jd.jr.pmock.demo.dao;

import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Result;
import com.jd.jr.pmock.demo.vo.Shopping;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟dao实现层
 * User: yangkuan@jd.com
 * Date: 18-5-23
 * Time: 下午8:31
 */
@Service
public class PersonBusinessDaoImpl implements PersonBusinessDao {


    public PersonVo queryAge(PersonQuery query) {
        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        return person;

    }


    public PersonVo queryName(PersonQuery query) {
        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        return person;
    }

    public List<PersonVo> queryPersonList(PersonQuery query) {

        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        List<PersonVo> personArrayList = new ArrayList<PersonVo>();
        personArrayList.add(person);
        return personArrayList;
    }

    public Map<String, PersonVo> queryPersonMap(PersonQuery query) {

        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        Map<String, PersonVo> personMap = new HashMap<String, PersonVo>();
        personMap.put("key", person);

        return personMap;
    }


    public Shopping queryShopping(PersonQuery query) {
        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        Shopping shopping = new Shopping();
        shopping.setDesc("血拼的人");
        shopping.setPerson(person);
        return shopping;
    }

    public Result<Shopping> queryShoppingResult(PersonQuery query) {
        PersonVo person = new PersonVo();
        person.setAge(query.getAge());
        person.setName(query.getName());
        Shopping shopping = new Shopping();
        shopping.setDesc("血拼的人");
        shopping.setPerson(person);
        Result mockResult = new Result();
        mockResult.setFlag(true);
        mockResult.setDesc("查询血拼");
        mockResult.setValue(shopping);
        return mockResult;
    }

    @Override
    public void queryVoid() {

    }

    @Override
    public PersonVo queryComplexShopping(Result<Shopping> shoppingResult, PersonQuery query) {
        return null;
    }

    @Override
    public int queryCount(int count) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer queryCountInter(Integer integer) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String queryString(String string) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}