package com.kingdee.service.impl;

import com.kingdee.dao.MaterialInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.Material;
import com.kingdee.service.MaterialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Resource
    private MaterialInspurMapper materialInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(Material marerial) throws Exception {
        if (materialInspurMapper.selectByPrimaryKey(marerial) == null) {
            materialInspurMapper.insertSelective(marerial);
        } else {
            materialInspurMapper.updateByPrimaryKeySelective(marerial);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(Material marerial) throws Exception {
        if (materialInspurMapper.selectByPrimaryKey(marerial) == null) {
            materialInspurMapper.insertSelective(marerial);
        } else {
            materialInspurMapper.updateByPrimaryKeySelective(marerial);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(Material material) throws Exception {
        int count = materialInspurMapper.selectCount(material);
        return count > 0 ? true : false;
    }
}
