package test.frame.spock

import com.jd.jr.pmock.demo.dao.PersonBusinessDao
import com.jd.jr.pmock.demo.service.PersonBusinessServiceImpl
import com.jd.jr.pmock.demo.vo.PersonVo
import spock.lang.Specification
/**
 * User: yangkuan@jd.com
 * Date: 18-5-31
 * Time: 下午5:15
 */
class PersonBusinessServiceTest  extends Specification {
    def 'PersonBusinessService 查询学生列表'(){
        given: '传入age为#age，name#name，sex#sex' +
                '模拟PersonBusinessDao的方法queryPersonList'
         List<PersonVo> returenList = new ArrayList<PersonVo>();
         def personVo = new PersonVo(age:ageInput,name:nameInput,sex:sexInput);
         returenList.add(personVo)
        def expectedReturnVo = returenList;
        def mockPersonBusinessDao = Mock(PersonBusinessDao.class);

        and:  '设置返回值'
        mockPersonBusinessDao.queryPersonList(_) >> expectedReturnVo;

        when:  '当PersonBusinessService依赖mock对象进行查询'
        def service = new PersonBusinessServiceImpl(
                personBusinessDao :  mockPersonBusinessDao);

        def obj =  service.queryStudents(inputSext);
        then:'验证'
         print '学生数量='
         println obj.size()

        where:'不同的模拟数据'
        ageInput << [2,17,11]
        nameInput<<['jim','tom','mike']
        sexInput<<[1 ,1,1 ]
        inputSext<<[1 ,2,1 ]
    }
}
