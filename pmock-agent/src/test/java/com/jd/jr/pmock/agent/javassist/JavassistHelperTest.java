package com.jd.jr.pmock.agent.javassist;

import com.jd.jr.pmock.agent.loader.PersonVo;
import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 上午9:44
 */
public class JavassistHelperTest {
    @Test
    public void getNewOjectTest() throws Exception{
        //PersonVo personVo  =   JavassistHelper.getNewOject("om.jd.jr.pmock.agent.loader.PersonVo");
        PersonVo personVo  =   JavassistHelper.getNewOject(PersonVo.class);

        System.out.println(personVo.getName());
    }

}
