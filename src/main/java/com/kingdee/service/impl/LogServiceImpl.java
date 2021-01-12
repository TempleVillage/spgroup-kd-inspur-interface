package com.kingdee.service.impl;

import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.service.LogService;
import com.kingdee.model.vo.Visitlog;
import com.kingdee.dao.VisitlogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author songsongpeijun
 * @title: LogServiceImpl
 * @projectName door
 * @description: TODO
 * @date 2020/11/310:41 上午
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private VisitlogMapper visitlogMapper;

    @DataSource("first")
    @Override
    public void save(Visitlog visitlog) {
        //System.out.println(visitlog.getId().toString());
        visitlogMapper.insert(visitlog);
    }
}
