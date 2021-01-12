package com.kingdee.service.impl;

import com.kingdee.dao.MaterialTypeInspurMapper;
import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.model.po.MaterialType;
import com.kingdee.service.MaterialTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jambin
 * @description
 * @date 2020/11/6 14:07
 */
@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {
    @Resource
    private MaterialTypeInspurMapper materialTypeInspurMapper;

    @DataSource("first")
    @Override
    public void saveLocal(MaterialType materialType) throws Exception {
        if (materialTypeInspurMapper.selectByPrimaryKey(materialType) == null) {
            materialTypeInspurMapper.insertSelective(materialType);
        } else {
            materialTypeInspurMapper.updateByPrimaryKeySelective(materialType);
        }
    }

    @DataSource("second")
    @Override
    public void saveInspur(MaterialType materialType) throws Exception {
        if (materialTypeInspurMapper.selectByPrimaryKey(materialType) == null) {
            materialTypeInspurMapper.insertSelective(materialType);
        } else {
            materialTypeInspurMapper.updateByPrimaryKeySelective(materialType);
        }
    }

    @DataSource("second")
    @Override
    public boolean isExist(MaterialType materialType) throws Exception {
        int count = materialTypeInspurMapper.selectCount(materialType);
        return count > 0 ? true : false;
    }
}
