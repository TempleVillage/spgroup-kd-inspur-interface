package com.kingdee.service.impl;

import com.kingdee.dao.AreaInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Area;
import com.kingdee.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaInspurMapper areaInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Area area) throws Exception {
        Area temp = areaInspurMapper.selectByPrimaryKey(area);
        if (temp == null) {
            areaInspurMapper.insertSelective(area);
        } else {
            areaInspurMapper.updateByPrimaryKeySelective(area);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Area area) throws Exception {
        Area temp = areaInspurMapper.selectByPrimaryKey(area);
        if (temp == null) {
            areaInspurMapper.insertSelective(area);
        } else {
            areaInspurMapper.updateByPrimaryKeySelective(area);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Area area) throws Exception {
        int count = areaInspurMapper.selectCount(area);

        return count > 0 ? true : false;
    }

}
