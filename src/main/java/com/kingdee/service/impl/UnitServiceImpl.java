package com.kingdee.service.impl;

import com.kingdee.dao.UnitInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Unit;
import com.kingdee.service.UnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UnitServiceImpl implements UnitService {
    @Resource
    private UnitInspurMapper unitInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Unit unit) throws Exception {
        if (unitInspurMapper.selectByPrimaryKey(unit) == null) {
            unitInspurMapper.insertSelective(unit);
        } else {
            unitInspurMapper.updateByPrimaryKeySelective(unit);
        }

    }

    @DataSource("second")
    @Override
    public void saveInspur(Unit unit) throws Exception {
        if (unitInspurMapper.selectByPrimaryKey(unit) == null) {
            unitInspurMapper.insertSelective(unit);
        } else {
            unitInspurMapper.updateByPrimaryKeySelective(unit);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Unit unit) throws Exception {
        int count = unitInspurMapper.selectCount(unit);
        return count > 0 ? true : false;
    }
}
