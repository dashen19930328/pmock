package com.jd.jr.pmock.demo.service;

import com.jd.jr.pmock.demo.vo.*;

import java.util.List;
import java.util.Map;

/**
 * 模拟dao
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午9:49
 */
public interface PersonBusinessService {

    /**
     *
     * @param sex
     * @return
     */
    public List<Student> queryStudents(Integer sex) ;

    public PersonVo queryAge(PersonQuery query);

    public PersonVo queryName(PersonQuery query);

    public List<PersonVo> queryPersonList(PersonQuery query);

    public Map<String, PersonVo> queryPersonMap(PersonQuery query);

    public Shopping queryShopping(PersonQuery query);

    public Result<Shopping> queryShoppingResult(PersonQuery query);

    public void queryVoid();


    public PersonVo queryComplex(PersonQuery query);

    public int queryCount();//测试基本类型
    public Integer queryCountInter();//测试基本类型的包装


    public String queryString();
}
