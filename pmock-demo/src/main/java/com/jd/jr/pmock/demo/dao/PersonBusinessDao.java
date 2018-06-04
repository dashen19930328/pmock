package com.jd.jr.pmock.demo.dao;

import com.jd.jr.pmock.demo.vo.PersonQuery;
import com.jd.jr.pmock.demo.vo.PersonVo;
import com.jd.jr.pmock.demo.vo.Result;
import com.jd.jr.pmock.demo.vo.Shopping;

import java.util.List;
import java.util.Map;

/**
 * 模拟dao
 * User: yangkuan@jd.com
 * Date: 18-5-28
 * Time: 下午9:49
 */
public interface PersonBusinessDao {
    public PersonVo queryAge(PersonQuery query);

    public PersonVo queryName(PersonQuery query);

    public List<PersonVo> queryPersonList(PersonQuery query);

    public Map<String, PersonVo> queryPersonMap(PersonQuery query);

    public Shopping queryShopping(PersonQuery query);

    public Result<Shopping> queryShoppingResult(PersonQuery query);


    public void queryVoid();

    /**
     * 随意编造复杂的接口
     * @param shoppingResult
     * @param query
     * @return
     */
    public PersonVo queryComplexShopping(Result<Shopping> shoppingResult,PersonQuery query);

    public int queryCount(int count);//测试基本类型
    public Integer queryCountInter(Integer count);//测试基本类型的包装

    public String queryString(String string);


}
