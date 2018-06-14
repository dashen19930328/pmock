package com.jd.jr.pmock.server.dao;


import com.jd.jr.pmock.server.domain.CaseConfigVo;
import com.jd.jr.pmock.server.query.PageQuery;

import java.util.List;

public interface CaseConfigMapper {

   // public List<CaseConfigVo> queryBySelective(Query<CaseConfigVo> caseConfig);

    public Long queryCountBySelective(CaseConfigVo caseConfigVo);
    public List<CaseConfigVo> queryBySelective(CaseConfigVo caseConfigVo);

    public CaseConfigVo queryByPrimaryKey(Long id);

    public Integer save(CaseConfigVo caseConfigVo);

    public Integer updateByPrimaryKey(CaseConfigVo caseConfigVo);

   public List<CaseConfigVo> queryBySelectiveForPagination(PageQuery<CaseConfigVo> caseConfig);


}
