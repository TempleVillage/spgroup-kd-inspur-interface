package com.kingdee.service.impl;

import com.kingdee.dao.InAndOutInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.InAndOut;
import com.kingdee.service.InAndOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jambin
 * @description
 * @date 2020/11/6 14:15
 */
@Service
public class InAndOutServiceImpl implements InAndOutService {
    @Resource
    private InAndOutInspurMapper inAndOutInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(InAndOut inAndOut) throws Exception {
        if (inAndOutInspurMapper.selectByPrimaryKey(inAndOut) == null) {
            inAndOutInspurMapper.insertSelective(inAndOut);
        } else {
            inAndOutInspurMapper.updateByPrimaryKeySelective(inAndOut);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(InAndOut inAndOut) throws Exception {
        if (inAndOutInspurMapper.selectByPrimaryKey(inAndOut) == null) {
            inAndOutInspurMapper.insertSelective(inAndOut);
        } else {
            inAndOutInspurMapper.updateByPrimaryKeySelective(inAndOut);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(InAndOut inAndOut) throws Exception {
        int count = inAndOutInspurMapper.selectCount(inAndOut);
        return count > 0 ? true : false;
    }

}
