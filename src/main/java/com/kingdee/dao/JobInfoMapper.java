package com.kingdee.dao;


import com.kingdee.model.bo.JobInfoBO;
import com.kingdee.model.po.JobInfo;
import com.kingdee.model.query.JobInfoQuery;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zxq
 */
public interface JobInfoMapper extends Mapper<JobInfo> {

    /**
     * 查询jobInfo
     * @param query
     * @return
     */
    List<JobInfoBO> selectJobInfo(@Param(value = "query") JobInfoQuery query);

}