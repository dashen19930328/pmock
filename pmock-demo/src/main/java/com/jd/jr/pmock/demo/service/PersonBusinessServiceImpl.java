package com.jd.jr.pmock.demo.service;

import com.jd.jr.pmock.demo.dao.PersonBusinessDao;
import com.jd.jr.pmock.demo.rpc.PlayRpc;
import com.jd.jr.pmock.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service("personBusinessService")
public class PersonBusinessServiceImpl implements PersonBusinessService {

    @Autowired
    PersonBusinessDao personBusinessDao;

    //需要配置groovy的case
    @Resource(name="playRpc")//因为自己写的rpc代理框架，不能使用Autowired注入
    PlayRpc playRpc;
    @Override
    public List<Student> queryStudents(Integer sex) {
        PersonQuery query = new PersonQuery();
        query.setSex(sex);
        List<PersonVo> personVoList = personBusinessDao.queryPersonList(query);
        if(playRpc!=null){
            playRpc.play();//纯粹mock打杂，第三方系统提供的接口示例
        }
        List<Student> studentList = null;
        if(personVoList!=null&&personVoList.size()>0){
            studentList = new ArrayList<Student>();
            for(PersonVo personVo : personVoList){
                if(personVo.getAge()==null){
                    continue;
                }
                if(personVo.getAge()<=3){
                    continue;
                }
                if(personVo.getAge()>=16){
                    continue;
                }
                Student student = new Student();
                if(personVo.getAge()>3&&personVo.getAge()<=6){
                    student.setSchool("幼儿园");
                }
                if(personVo.getAge()>6&&personVo.getAge()<=12){
                    student.setSchool("小学");
                }
                if(personVo.getAge()>12&&personVo.getAge()<16){
                    student.setSchool("中学");
                }
                student.setAge(personVo.getAge());
                student.setName(personVo.getName());
                student.setSex(personVo.getSex());
                studentList.add(student);
            }
        }
        return studentList;
    }

    public PersonVo queryAge(PersonQuery query) {
        PersonVo personVo = personBusinessDao.queryAge(query);
        //实际情况中后面一堆if else 各种逻辑
        if (personVo != null) {

        }
        return personVo;
    }

    public PersonVo queryName(PersonQuery query) {
        PersonVo personVo = personBusinessDao.queryName(query);
        //实际情况中后面一堆if else 各种逻辑
        return personVo;
    }

    public List<PersonVo> queryPersonList(PersonQuery query) {
        List<PersonVo> personArrayList = personBusinessDao.queryPersonList(query);
        //实际情况中后面一堆if else 各种逻辑
        if (personArrayList != null && personArrayList.size() > 0) {

        } else {
            PersonVo person = new PersonVo();
            person.setName("查询人员名字");
            personArrayList = new ArrayList<PersonVo>();
            personArrayList.add(person);
        }
        return personArrayList;
    }

    public Map<String, PersonVo> queryPersonMap(PersonQuery query) {
        PersonVo personVo = personBusinessDao.queryAge(query);
        Map<String, PersonVo> personMap = new HashMap<String, PersonVo>();
        personMap.put("aop", personVo);
        return personMap;
    }


    public Shopping queryShopping(PersonQuery query) {
        Shopping shopping = personBusinessDao.queryShopping(query);
        return shopping;
    }

    public Result<Shopping> queryShoppingResult(PersonQuery query) {
        Shopping shopping = personBusinessDao.queryShopping(query);
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
    public PersonVo queryComplex(PersonQuery query) {
        query = new PersonQuery();
        query.setSex(1);
        Shopping shopping = new Shopping();
        PersonVo person = new PersonVo();
        person.setAge(12);
        shopping.setPerson(person);
        shopping.setDesc("test");
        Result<Shopping> shoppingResult = new Result<Shopping>();
        shoppingResult.setValue(shopping);
       return personBusinessDao.queryComplexShopping(shoppingResult,query);
    }

    @Override
    public int queryCount() {
      return   personBusinessDao.queryCount(1);
    }

    @Override
    public Integer queryCountInter() {
        return   personBusinessDao.queryCountInter(2);
    }

    @Override
    public String queryString() {
        return personBusinessDao.queryString("string");
    }


    public PersonBusinessDao getPersonBusinessDao() {
        return personBusinessDao;
    }

    public void setPersonBusinessDao(PersonBusinessDao personBusinessDao) {
        this.personBusinessDao = personBusinessDao;
    }
}