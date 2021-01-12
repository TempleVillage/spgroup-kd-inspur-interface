package com.kingdee.dao;

import com.kingdee.model.bo.JobLogBO;
import com.kingdee.model.po.JobLog;
import com.kingdee.model.query.JobLogQuery;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zxq
 */
public interface JobLogMapper extends Mapper<JobLog> {

    /**
     * 查找任务运行日志
     * @param jobLogQuery
     * @return
     */
    List<JobLogBO> selectJobLog(@Param(value = "query") JobLogQuery jobLogQuery);

}